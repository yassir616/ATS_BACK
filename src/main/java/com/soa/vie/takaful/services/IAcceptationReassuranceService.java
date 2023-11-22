package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateReassuranceModel;
import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationReassuranceModelResponse;

public interface IAcceptationReassuranceService {

    public AcceptationReassuranceModelResponse createAcceptationReassurance (CreateallAcceptationTestKindModelRequest model) throws InterruptedException, ExecutionException;

    public List<AcceptationReassuranceModelResponse> acceptationReassuranceByAcceptation(String acceptationId) throws InterruptedException, ExecutionException;

    public AcceptationReassuranceModelResponse updateAcceptationReassurance(CreateReassuranceModel model,String id) throws InterruptedException, ExecutionException ;
}