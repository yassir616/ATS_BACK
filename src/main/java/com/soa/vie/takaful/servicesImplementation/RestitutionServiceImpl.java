package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Restitution;
import com.soa.vie.takaful.repositories.IRestitutionRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateRestitution;
import com.soa.vie.takaful.responsemodels.RestitutionResponseModel;
import com.soa.vie.takaful.services.IRestitutionService;
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
public class RestitutionServiceImpl implements IRestitutionService {
    
    @Autowired
    private IRestitutionRepository restitutionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public RestitutionResponseModel createRestitution(CreateAndUpdateRestitution restitution)throws InterruptedException,ExecutionException {
    	log.info("creation restitutions ...");
        Thread.sleep(1000L);
        Restitution addrestitution = new Restitution();
        String libelle = restitution.getLibelle();

        
        Optional<Restitution> restitutionexists = restitutionRepository.findByLibelle(libelle);
        
        if (restitutionexists.isPresent()) {
            log.info("restitution already exist");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"cette restitution existe déja");
        }
     
        else {

            BeanUtils.copyProperties(restitution, addrestitution);
            addrestitution = this.restitutionRepository.save(addrestitution);

            
        }
        return modelMapper.map(addrestitution,RestitutionResponseModel.class);

    }

    @Override
    @Async("asyncExecutor")
    public RestitutionResponseModel updateRestitution(String restitutionId, CreateAndUpdateRestitution restitutionModel) throws InterruptedException,ExecutionException{
        Thread.sleep(1000L);
        java.util.Optional<Restitution> restitutionOptional = restitutionRepository.findById(restitutionId);
        if (!restitutionOptional.isPresent()) {
            log.info("no restitution with the id : " + restitutionId);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"ce ID n'existe pas");
        }
        Restitution restitutionEntity = restitutionOptional.get();
        restitutionEntity.setLibelle(restitutionModel.getLibelle());

        return modelMapper.map(restitutionRepository.save(restitutionEntity),RestitutionResponseModel.class);
    }

    @Override
    @Async("asyncExecutor")
    public Page<RestitutionResponseModel> getRestitution(int page, int limit, String sort, String direction) throws InterruptedException,ExecutionException{
    	log.info("lister les restitutions");
        Thread.sleep(1000L);
        return restitutionRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, RestitutionResponseModel.class));
    }

    @Override
    @Async("asyncExecutor")
    public RestitutionResponseModel getRestitutionById(String id) throws InterruptedException,ExecutionException{
        Thread.sleep(1000L);
        java.util.Optional<Restitution> restitutionOptional = restitutionRepository.findById(id);
        if (!restitutionOptional.isPresent()) {
            log.info("ce ID n'existe pas : " + id);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"il y'a aucune restitution avec ce id");
        } else {

            return modelMapper.map(restitutionOptional.get(),RestitutionResponseModel.class);

        }
    }

}
