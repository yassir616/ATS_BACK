package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationLaboratoireModelResponse;

public interface IAcceptationLaboratoireService {

    public AcceptationLaboratoireModelResponse createAcceptationLaboratoire (CreateallAcceptationTestKindModelRequest model) throws InterruptedException, ExecutionException;

    public List<AcceptationLaboratoireModelResponse> acceptationsLaboratoireByAcceptation(String acceptation) throws InterruptedException, ExecutionException;

}