package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.entitymodels.TypePrestation;
import com.soa.vie.takaful.entitymodels.TypePrestationProduit;
import com.soa.vie.takaful.repositories.IProduitRepository;
import com.soa.vie.takaful.repositories.ITypePrestationProduitRepository;
import com.soa.vie.takaful.repositories.ITypePrestationRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePrestationProduit;
import com.soa.vie.takaful.responsemodels.TypePrestationProduitModelResponse;
import com.soa.vie.takaful.services.ITypePrestationProduitService;
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
public class TypePrestationProduitServiceImpl implements ITypePrestationProduitService {

	private static final String ERRORMESSAGE = "La modification d'un type prestation produit avec ce Id est érroné ";

	@Autowired
    private IProduitRepository produitRepository;
    
    @Autowired
	private ITypePrestationRepository typePrestationRepository;
	
    @Autowired
    private ITypePrestationProduitRepository typePrestationProduitRepository;

	@Autowired
	private ModelMapper modelMapper;
    
	@Override
	@Async("asyncExecutor")
	public TypePrestationProduitModelResponse createTypePrestationProduit(CreateAndUpdateTypePrestationProduit model)throws InterruptedException, ExecutionException {
		log.info("creation d'un type prestation produit ...");

		Thread.sleep(1000L);
        TypePrestationProduit prestationproduit = new TypePrestationProduit();

        if (model.getProduitId() != null && !"".equals(model.getTypePrestationId())) {

            Optional<Produit> produit = produitRepository.findById(model.getProduitId());
            Optional<TypePrestation> prestation = typePrestationRepository.findById(model.getTypePrestationId());


            if(produit.isPresent() && prestation.isPresent()){
            	prestationproduit.setProduit(produit.get());

            	prestationproduit.setTypePrestation(prestation.get());

                BeanUtils.copyProperties(model, prestationproduit);
				return modelMapper.map(typePrestationProduitRepository.save(prestationproduit)
						, TypePrestationProduitModelResponse.class);
            }else{
                log.error("Erreur creation d'un type prestation produit ");
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Erreur creation d'un type prestation produit ,wrong id");
            }
        } else {
            log.error("Erreur creation d'un type prestation produit you need to insert all required values");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Erreur creation d'un type prestation produit you need to insert all required values");
        }
   
	}

	@Override
	@Async("asyncExecutor")
	public TypePrestationProduitModelResponse updateTypePrestationProduit(String id, CreateAndUpdateTypePrestationProduit model)throws InterruptedException, ExecutionException {
		log.info("modifier le type prestation produit avec l'Id : {}", id);

		Thread.sleep(1000L);
		if (model.getProduitId() != null && !"".equals(model.getTypePrestationId())) {
			Optional<TypePrestationProduit> prestationProduitOpt = typePrestationProduitRepository.findById(id);
			Optional<Produit> produit = produitRepository.findById(model.getProduitId());
			Optional<TypePrestation> prestation = typePrestationRepository.findById(model.getTypePrestationId());

			if (prestationProduitOpt.isPresent() && produit.isPresent() && prestation.isPresent()) {
				TypePrestationProduit prestationprod = prestationProduitOpt.get();
				prestationprod.setProduit(produit.get());
				prestationprod.setTypePrestation(prestation.get());
				BeanUtils.copyProperties(model, prestationprod);
				return modelMapper.map(typePrestationProduitRepository.save(prestationprod)
						, TypePrestationProduitModelResponse.class);

			} else {
				log.error(ERRORMESSAGE);
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ERRORMESSAGE);
			}
		} else {
			log.error(ERRORMESSAGE);
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ERRORMESSAGE);
		}
	}

	@Override
	@Async("asyncExecutor")
	public Page<TypePrestationProduitModelResponse> getTypePrestationProduits(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {
		log.info("Getting categorie");

		Thread.sleep(1000L);
		return typePrestationProduitRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, TypePrestationProduitModelResponse.class));

	}

}
