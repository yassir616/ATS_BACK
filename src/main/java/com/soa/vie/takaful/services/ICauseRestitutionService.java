package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdateOneStringRequestModel;

import com.soa.vie.takaful.responsemodels.CauseRestitutionModelResponse;
import org.springframework.data.domain.Page;

public interface ICauseRestitutionService {

    public CauseRestitutionModelResponse createCauseRestitution(CreateUpdateOneStringRequestModel libelle) throws InterruptedException, ExecutionException;

    public CauseRestitutionModelResponse updateCauseRestitution(CreateUpdateOneStringRequestModel libelle,
            String typePrestationId) throws InterruptedException, ExecutionException;

    public Page<CauseRestitutionModelResponse> getCauseRestitution(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;
}