package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationSpecialisteModelResponse;

public interface IAcceptationSpecialisteService {

    public AcceptationSpecialisteModelResponse createAcceptationSpecialiste (CreateallAcceptationTestKindModelRequest model) throws InterruptedException, ExecutionException;

    public List<AcceptationSpecialisteModelResponse> acceptationsSpecialisteByAcceptation (String acceptation) throws InterruptedException, ExecutionException;
    
    public AcceptationSpecialisteModelResponse updateAcceptationSpecialiste (CreateallAcceptationTestKindModelRequest model,String id) throws InterruptedException, ExecutionException;

}