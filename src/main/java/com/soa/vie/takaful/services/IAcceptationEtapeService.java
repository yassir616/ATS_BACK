package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAcceptationEtapeModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationEtapeModelResponse;

public interface IAcceptationEtapeService {
    public AcceptationEtapeModelResponse createAcceptationEtape(CreateAcceptationEtapeModelRequest model) throws InterruptedException, ExecutionException;

}