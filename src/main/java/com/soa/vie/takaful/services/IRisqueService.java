package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateRisqueRequestModel;

import com.soa.vie.takaful.responsemodels.RisqueResponseModel;
import org.springframework.data.domain.Page;

public interface IRisqueService {

    public RisqueResponseModel createRisque(CreateAndUpdateRisqueRequestModel requestModel)
            throws InterruptedException, ExecutionException;

    public RisqueResponseModel updateRisque(String riqueId, CreateAndUpdateRisqueRequestModel requestModel)
            throws InterruptedException, ExecutionException;

    public RisqueResponseModel risqueById(String id) throws InterruptedException, ExecutionException;

    public Page<RisqueResponseModel> getAllRisque(int page, int limit, String sort, String direction, String theme)
            throws InterruptedException, ExecutionException;

}