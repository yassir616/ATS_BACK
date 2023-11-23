package com.soa.vie.takaful.services.Impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.soa.vie.takaful.entitymodels.PoolInvestissment;
import com.soa.vie.takaful.repositories.IPoolInvestissmentRepository;
import com.soa.vie.takaful.requestmodels.CreateUpdateOneStringRequestModel;
import com.soa.vie.takaful.responsemodels.PoolInvestissmentResponseModel;
import com.soa.vie.takaful.services.IPoolInvetissmentService;
import com.soa.vie.takaful.util.Pagination;

import org.bouncycastle.openssl.EncryptionException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PoolInvestissmentServiceImpl implements IPoolInvetissmentService {
    private String errorCreate = "Error creating Pool Invesitssmnt libelle is required";

    @Autowired
    private IPoolInvestissmentRepository poolInvestissmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public PoolInvestissmentResponseModel createPoolInvestissment(CreateUpdateOneStringRequestModel model) throws InterruptedException, EncryptionException {
        log.info("Creating Pool Investissment");
        Thread.sleep(1000L);
        PoolInvestissment poolInvestissment = new PoolInvestissment();
        if (!"".equals(model.getLibelle())) {
            poolInvestissment.setLibelle(model.getLibelle());
            return modelMapper.map(poolInvestissmentRepository.save(poolInvestissment),
                    PoolInvestissmentResponseModel.class);
        } else {
            log.error(errorCreate);
            throw new IllegalArgumentException(errorCreate);
        }
    }

    @Override
    @Async("asyncExecutor")
    public PoolInvestissmentResponseModel updatePoolInvestissment(CreateUpdateOneStringRequestModel model, String id) throws InterruptedException, EncryptionException{
        log.info("Updating pool investissment with Id : {}", id);
        Thread.sleep(1000L);
        if (!"".equals(model.getLibelle())) {
            Optional<PoolInvestissment> optionalPoolInvestissment = poolInvestissmentRepository.findById(id);
            if (optionalPoolInvestissment.isPresent()) {
                PoolInvestissment poolInvestissment = optionalPoolInvestissment.get();
                poolInvestissment.setLibelle(model.getLibelle());
                return modelMapper.map(poolInvestissmentRepository.save(poolInvestissment),
                        PoolInvestissmentResponseModel.class);
            } else {
                log.error("Error updating Pool investissment with Id ({}): No Pool investissment with the given Id",
                        id);
                throw new NoSuchElementException(
                        "Error updating Pool Investissment : No pool investissment with the given Id");
            }

        } else {
            log.error("Error updating pool investissment with Id ({}) libelle is required", id);
            throw new IllegalArgumentException(errorCreate);
        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<PoolInvestissmentResponseModel> getPoolInvestissments(int page, int limit, String sort,
            String direction)throws InterruptedException, EncryptionException {
        log.info("Getting pool investissment");
        Thread.sleep(1000L);
        return poolInvestissmentRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, PoolInvestissmentResponseModel.class));
    }
}
