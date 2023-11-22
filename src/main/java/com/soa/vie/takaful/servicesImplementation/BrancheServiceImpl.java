package com.soa.vie.takaful.servicesImplementation;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Branche;
import com.soa.vie.takaful.repositories.IBrancheRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateBranche;
import com.soa.vie.takaful.responsemodels.BrancheModelResponse;
import com.soa.vie.takaful.services.IBrancheService;
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
public class BrancheServiceImpl implements IBrancheService {
    @Autowired
    private IBrancheRepository brancheRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public BrancheModelResponse createBranche(CreateAndUpdateBranche branche) throws InterruptedException, ExecutionException{

        Thread.sleep(1000L);
        Branche addBranche = new Branche();
     
        String code = branche.getCode();
       

        Optional<Branche> brancheexists = brancheRepository.findByCode(code);

        if (brancheexists.isPresent()) {
            log.info("Branche already exist");
            throw new IllegalArgumentException("cette branche existe déja");
        }

        else {

            BeanUtils.copyProperties(branche, addBranche);

            addBranche = this.brancheRepository.save(addBranche);

        }
        return modelMapper.map(addBranche,BrancheModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public BrancheModelResponse updateBranche(String brancheId, CreateAndUpdateBranche brancheModel)throws InterruptedException, ExecutionException{

        Thread.sleep(1000L);
        java.util.Optional<Branche> brancheOptional= brancheRepository.findById(brancheId);
        if (!brancheOptional.isPresent()) {
            log.info("no branche with the id : " + brancheId);
            throw new NoSuchElementException("ce ID n'existe pas");
        }
        Branche brancheEntity = brancheOptional.get();
        brancheEntity.setCode(brancheModel.getCode());
        brancheEntity.setIcon(brancheModel.getIcon());
        brancheEntity.setLibelle(brancheModel.getLibelle());

        return modelMapper.map(brancheRepository.save(brancheEntity),BrancheModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public Page<BrancheModelResponse> getBranches(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {

        log.info("lister les branches");
        Thread.sleep(1000L);
        return brancheRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o,BrancheModelResponse.class));

    }

    @Override
    @Async("asyncExecutor")
    public BrancheModelResponse getBrancheById(String id) throws InterruptedException, ExecutionException{

        Thread.sleep(1000L);
        java.util.Optional<Branche> brancheOptional = brancheRepository.findById(id);
        if (!brancheOptional.isPresent()) {
            log.info("ce ID n'existe pas : " + id);
            throw new NoSuchElementException("il y'a aucune branche avec ce id");
        } else {
            return modelMapper.map(brancheOptional.get(),BrancheModelResponse.class);

        }
    }
}