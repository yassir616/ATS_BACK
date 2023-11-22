package com.soa.vie.takaful.servicesImplementation;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Commission;
import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.repositories.ICommissionRepository;
import com.soa.vie.takaful.repositories.IProduitRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCommission;
import com.soa.vie.takaful.responsemodels.CommissionResponseModel;
import com.soa.vie.takaful.services.ICommissionService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommissionServiceImpl implements ICommissionService {
	@Autowired
	private IProduitRepository produitRepository;

	@Autowired
	private ICommissionRepository commissionrepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public CommissionResponseModel createCommission(CreateAndUpdateCommission model)throws InterruptedException, ExecutionException {
		log.info("creation de  la commission ...");

		Thread.sleep(1000L);
		Commission commission = new Commission();

		if (!"".equals(model.getProduitId())) {

			BeanUtils.copyProperties(model, commission);
			return modelMapper.map(commissionrepository.save(commission), CommissionResponseModel.class);

		} else {
			log.error("la creation de la commission est erroné, merci d'insérer les champs obligatoire");
			throw new IllegalArgumentException(
					"la creation de la commission est erroné, merci d'insérer les champs obligatoire");
		}
	}

	@Override
	@Async("asyncExecutor")
	public CommissionResponseModel updateCommission(String commissionId, CreateAndUpdateCommission model) throws InterruptedException, ExecutionException{
		log.info("modifier la commission avec l'Id : {}", commissionId);
		Thread.sleep(1000L);

		if (!"".equals(model.getProduitId())) {

			Optional<Commission> commissionOpt = commissionrepository.findById(commissionId);
			Optional<Produit> produit = produitRepository.findById(model.getProduitId());

			if (commissionOpt.isPresent() && produit.isPresent()) {
				Commission commission = commissionOpt.get();
				commission.setProduit(produit.get());

				BeanUtils.copyProperties(model, commission);
				return modelMapper.map(commissionrepository.save(commission), CommissionResponseModel.class);

			} else {
				log.error("la modification de la commission est erroné avec l'ID({}): ", commissionId);
				throw new NoSuchElementException("la modification de la commission est erroné");
			}
		} else {
			log.error(
					"la modification  de la commission est erroné avec l'ID({}): Merci d'insérer les champs obligatoire",
					commissionId);
			throw new IllegalArgumentException(
					"la creation de la commission est erroné, Merci d'insérer les champs obligatoire");
		}
	}

	@Override
	@Async("asyncExecutor")
	public Page<CommissionResponseModel> getCommissions(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException{
		log.info("lister les commissions");

		Thread.sleep(1000L);
		return commissionrepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, CommissionResponseModel.class));
	}

}
