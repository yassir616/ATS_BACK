package com.soa.vie.takaful.services.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.entitymodels.Partenaire;
import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.entitymodels.ProduitMrb;
import com.soa.vie.takaful.entitymodels.TarificationMRB;
import com.soa.vie.takaful.repositories.IExclusionRepository;
import com.soa.vie.takaful.repositories.IPartenaireRepository;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.repositories.IProduitMrbRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateProduitMRB;
import com.soa.vie.takaful.requestmodels.CreateTarificationMRB;
import com.soa.vie.takaful.requestmodels.UpdateProductMRB;
import com.soa.vie.takaful.responsemodels.ProduitMrbModelResponse;
import com.soa.vie.takaful.services.IProduitMrbService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProduitMrbServiceImpl implements IProduitMrbService {
	@Autowired
	private IProduitMrbRepository produitMrbRepository;
	@Autowired
	private IPartenaireRepository partenaireRepository;
	@Autowired
	private IExclusionRepository exclusionRepository;
	@Autowired
	private IPeriodiciteRepository periodiciteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public ProduitMrbModelResponse createProduitMrb(CreateAndUpdateProduitMRB model) throws InterruptedException,ExecutionException{
		log.info("creation du produit mrb ...");
		Thread.sleep(1000L);
		ProduitMrb addProduct = new ProduitMrb();
		String libelle = model.getLibelle();
		Optional<ProduitMrb> productexists = produitMrbRepository.findByLibelle(libelle);
		BeanUtils.copyProperties(model, addProduct);

		if (productexists.isPresent()) {
			log.info("produit mrb already exist");
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "cette produit mrb existe déja");
		}

		else {
			if (null != model.getTarificationsMrb()) {
				List<TarificationMRB> tarrifications = new ArrayList<>();

				for (CreateTarificationMRB request : model.getTarificationsMrb()) {
					TarificationMRB tarrification = new TarificationMRB();
					BeanUtils.copyProperties(request, tarrification);
					tarrification.setProduitMrb(addProduct);
					tarrifications.add(tarrification);
				}

				addProduct.setTarificationsMrb(tarrifications);
			}
			java.util.Optional<Partenaire> partenaireOptional = partenaireRepository.findById(model.getPartenaireId());
			if (!partenaireOptional.isPresent()) {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Partenaire n'existe pas ");
			}
			Partenaire partenaire = partenaireOptional.get();
			addProduct.setPartenaire(partenaire);

			addProduct = this.produitMrbRepository.save(addProduct);
		}

		return modelMapper.map(addProduct, ProduitMrbModelResponse.class);
	}

	@Override
	@Async("asyncExecutor")
	public Page<ProduitMrbModelResponse> getAllProductMrb(int page, int limit, String sort, String direction) throws InterruptedException,ExecutionException{
		Thread.sleep(1000L);
		return produitMrbRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, ProduitMrbModelResponse.class));
	}

	@Override
	@Async("asyncExecutor")
	public ProduitMrbModelResponse updateProduitMrb(String id, UpdateProductMRB model) throws InterruptedException,ExecutionException{
		log.info("updating MRB product : {}", id);
		Thread.sleep(1000L);
		Optional<ProduitMrb> productexists = produitMrbRepository.findById(id);
		Optional<Partenaire> partenaire = partenaireRepository.findById(model.getPartenaireId());
		if (productexists.isPresent() && partenaire.isPresent()) {

			ProduitMrb mrbproduit = productexists.get();
			mrbproduit.setPartenaire(partenaire.get());
			if (null != model.getExclusionsProduit()) {
				List<Exclusion> exclusions = new ArrayList<>();
				for (Exclusion request : model.getExclusionsProduit()) {
					Optional<Exclusion> exclusionOptional = exclusionRepository.findById(request.getId());
					if (!exclusionOptional.isPresent()) {
						throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
								"UpdateProduitMrb : Exclusion n'existe pas ");
					}
					Exclusion exclusion = exclusionOptional.get();
					exclusions.add(exclusion);
				}
				mrbproduit.setExclusionsProduit(exclusions);
			}

			exclusionsHandler(model, mrbproduit);

			if (null != model.getPeriodicitesMrb()) {
				periodiciteMrbHandler(model, mrbproduit);
			}

			BeanUtils.copyProperties(model, mrbproduit);

			return modelMapper.map(produitMrbRepository.save(mrbproduit), ProduitMrbModelResponse.class);
		}

		else {
			log.error("la modification1 de produit est erroné avec l'ID({}): ", id);
			throw new NullPointerException("la modification1 de produit est erroné");
		}
	}

	protected void exclusionsHandler(UpdateProductMRB model, ProduitMrb mrbproduit) {
		List<Exclusion> exclusions = new ArrayList<>();
		for (Exclusion request : model.getExclusionsProduit()) {
			Optional<Exclusion> exclusionOptional = exclusionRepository.findById(request.getId());
			if (!exclusionOptional.isPresent()) {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "UpdateProduitMrb : Exclusion n'existe pas ");
			}
			Exclusion exclusion = exclusionOptional.get();
			exclusions.add(exclusion);
		}
		mrbproduit.setExclusionsProduit(exclusions);
	}

	protected void periodiciteMrbHandler(UpdateProductMRB model, ProduitMrb mrbproduit) {
		List<Periodicite> periodicites = new ArrayList<>();

		for (Periodicite request : model.getPeriodicitesMrb()) {
			Optional<Periodicite> periodiciteOptional = periodiciteRepository.findById(request.getId());
			if (!periodiciteOptional.isPresent()) {
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
						"UpdateProduitMrb : Periodicite n'existe pas ");
			}
			Periodicite periodicite = periodiciteOptional.get();
			periodicites.add(periodicite);
		}
		mrbproduit.setPeriodicitesMrb(periodicites);
	}

}
