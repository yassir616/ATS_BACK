package com.soa.vie.takaful.services;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePrestationRequestModel;


import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.TypePrestationModelResponse;

import org.springframework.data.domain.Page;

public interface ITypePrestationService {

    public TypePrestationModelResponse createTypePrestation(CreateAndUpdateTypePrestationRequestModel model) throws InterruptedException, ExecutionException;

    public TypePrestationModelResponse updateTypePrestation(CreateAndUpdateTypePrestationRequestModel model,
                                                            String typePrestationId) throws InterruptedException, ExecutionException;

    public TypePrestationModelResponse getTypePrestation(String typePrestationId) throws InterruptedException, ExecutionException;
    
    public List<TypePrestationModelResponse> getListPrestation(String id) throws InterruptedException, ExecutionException;

    public Page<TypePrestationModelResponse> getTypesPrestation(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

}