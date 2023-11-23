package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.CauseRestitution;
import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.entitymodels.DecesProduitCauseRestitution;
import com.soa.vie.takaful.entitymodels.Restitution;
import com.soa.vie.takaful.repositories.ICauseRestitutionRepository;
import com.soa.vie.takaful.repositories.IDecesProduitCauseRestitutionRepository;
import com.soa.vie.takaful.repositories.IDecesProduitRepository;
import com.soa.vie.takaful.repositories.IRestitutionRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateDecesProduitCauseRestitution;
import com.soa.vie.takaful.responsemodels.DecesProduitCauseRestitutionModelResponse;
import com.soa.vie.takaful.services.IDecesProdCauseRestService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Service
@Transactional
@Slf4j
public class DecesProdCauseRestServiceImpl implements IDecesProdCauseRestService{
	 @Autowired
	    private IDecesProduitRepository decesprodRepository;

	    @Autowired
	    private IRestitutionRepository restitutionRepository;

	    @Autowired
	    private ICauseRestitutionRepository causeRestitutionRepository;

	    @Autowired
	    private IDecesProduitCauseRestitutionRepository decesProdcauseRestRepository;

	    @Autowired
        private ModelMapper modelMapper;

	@Override
    @Async("asyncExecutor")
	public DecesProduitCauseRestitutionModelResponse createDecesProduitCauseRestitution(
			CreateAndUpdateDecesProduitCauseRestitution model)throws InterruptedException, ExecutionException {
		log.info("creation deces produit cause restitution ...");

        Thread.sleep(1000L);
        DecesProduitCauseRestitution resultreturn = new DecesProduitCauseRestitution();

        if (!"".equals(model.getIdCauseRestitution()) && !"".equals(model.getIdRestitution())) {
            Optional<DecesProduit> decesprod = decesprodRepository.findById(model.getIdDeces());
            Optional<Restitution> restitution = restitutionRepository.findById(model.getIdRestitution());
            Optional<CauseRestitution> causerest = causeRestitutionRepository.findById(model.getIdCauseRestitution());
            if(decesprod.isPresent() && restitution.isPresent() && causerest.isPresent()){


                resultreturn.setDeces(decesprod.get());
                resultreturn.setRestitution(restitution.get());
                resultreturn.setCauseRestitution(causerest.get());

                BeanUtils.copyProperties(model, resultreturn);

                resultreturn = decesProdcauseRestRepository.save(resultreturn);

                return modelMapper.map(resultreturn,DecesProduitCauseRestitutionModelResponse.class);
            }else{
                log.error("la creation de deces produit cause restitution est erroné");
                throw new NullPointerException("la creation de deces produit cause restitution est erroné");
            }
        } else {
            log.error("la creation de deces produit cause restitution est erroné, merci d'inséser tous les champs obligatoire");
            throw new NullPointerException("la creation de deces produit cause restitution est erroné,merci d'inséser tous les champs obligatoire");
        }

	}

	
	@Override
    @Async("asyncExecutor")
	public Page<DecesProduitCauseRestitutionModelResponse> getDecesProduitCauseRestitutions(int page, int limit, String sort,
                                                                                            String direction)throws InterruptedException, ExecutionException {
		log.info("lister les deces produits cause restitution");
        Thread.sleep(1000L);
        return decesProdcauseRestRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, DecesProduitCauseRestitutionModelResponse.class));
	}


	@Override
    @Async("asyncExecutor")
	public DecesProduitCauseRestitutionModelResponse updateDecesProduitCauseRestitution(String id,
                                                                                        CreateAndUpdateDecesProduitCauseRestitution model)throws InterruptedException, ExecutionException {
		log.info("modifier deces produit cause restitution avec Id : {}", id);
        Thread.sleep(1000L);
        if (!"".equals(model.getIdCauseRestitution())
        		&& !"".equals(model.getIdRestitution())
        		) {

            Optional<DecesProduitCauseRestitution> decescauseOpt = decesProdcauseRestRepository.findById(id);
            Optional<DecesProduit> decesprod = decesprodRepository.findById(model.getIdDeces());
            Optional<Restitution> restitution = restitutionRepository.findById(model.getIdRestitution());
            Optional<CauseRestitution> causerest = causeRestitutionRepository.findById(model.getIdCauseRestitution());


            if (decescauseOpt.isPresent() && decesprod.isPresent() && restitution.isPresent() && causerest.isPresent()) {
            	DecesProduitCauseRestitution decescauserest = decescauseOpt.get();
            	decescauserest.setCauseRestitution(causerest.get());
            	decescauserest.setDeces(decesprod.get());
            	decescauserest.setRestitution(restitution.get());
                BeanUtils.copyProperties(model, decescauserest);
                return modelMapper.map(decesProdcauseRestRepository.save(decescauserest),DecesProduitCauseRestitutionModelResponse.class);

            } else {
                log.error("la modification de produit cause restitution est erroné avec Id ({}):",id);
                throw new NullPointerException("la modification de produit cause restitution est erroné");
            }
        } else {
            log.error("la modification de produit cause restitution est erroné avec Id ({}): merci d'insérerles champs obligatoire", id);
            throw new NullPointerException("la modification de produit cause restitution est erroné merci d'insérerles champs obligatoire");
        }
	}

}
