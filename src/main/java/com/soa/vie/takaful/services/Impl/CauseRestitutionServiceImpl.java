package com.soa.vie.takaful.services.Impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.CauseRestitution;
import com.soa.vie.takaful.repositories.ICauseRestitutionRepository;
import com.soa.vie.takaful.requestmodels.CreateUpdateOneStringRequestModel;
import com.soa.vie.takaful.responsemodels.CauseRestitutionModelResponse;
import com.soa.vie.takaful.services.ICauseRestitutionService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CauseRestitutionServiceImpl implements ICauseRestitutionService {

    private String errorCreateCauseRestitution ="Error creating cause Restitution libelle is required";
    @Autowired
    private ICauseRestitutionRepository causeRestitutionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public CauseRestitutionModelResponse createCauseRestitution(CreateUpdateOneStringRequestModel model) throws InterruptedException, ExecutionException{

        log.info("creating Cause Restitution  ...");

        Thread.sleep(1000L);
        CauseRestitution causeRestitution = new CauseRestitution();

        if (!"".equals(model.getLibelle())) {

            causeRestitution.setLibelle(model.getLibelle());
            return modelMapper.map(causeRestitutionRepository.save(causeRestitution),CauseRestitutionModelResponse.class);
        } else {

            log.error(errorCreateCauseRestitution);
            throw new IllegalArgumentException(errorCreateCauseRestitution);
        }
    }

    @Override
    @Async("asyncExecutor")
    public CauseRestitutionModelResponse updateCauseRestitution(CreateUpdateOneStringRequestModel model, String causeRestitutionId)throws InterruptedException, ExecutionException {

        log.info("updating Cause Restitution with Id : {}", causeRestitutionId);

        Thread.sleep(1000L);
        if (!"".equals(model.getLibelle())) {

            Optional<CauseRestitution> optCauseRes = causeRestitutionRepository.findById(causeRestitutionId);

            if (optCauseRes.isPresent()) {

                CauseRestitution causeRestitution = optCauseRes.get();
                causeRestitution.setLibelle(model.getLibelle());
                return modelMapper.map(causeRestitutionRepository.save(causeRestitution),CauseRestitutionModelResponse.class);

            } else {
                log.error("Error updating cause Restitution with Id ({}): No cause Restitution with the given Id",
                        causeRestitutionId);
                throw new NoSuchElementException("Error updating cause Restitution : No cause Restitution with the given Id");
            }
        } else {
            log.error("Error updating cause Restitution with Id ({}) libelle is required", causeRestitutionId);
            throw new IllegalArgumentException(errorCreateCauseRestitution);

        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<CauseRestitutionModelResponse> getCauseRestitution(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {

        log.info("Getting  Restitution causes");
        Thread.sleep(1000L);
        return causeRestitutionRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, CauseRestitutionModelResponse.class));
    }
}