package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationConseilModelResponse;

public interface IAcceptationConseilService {

    public AcceptationConseilModelResponse createAcceptationConseil (CreateallAcceptationTestKindModelRequest model) throws InterruptedException, ExecutionException;
    public List<AcceptationConseilModelResponse> acceptationConseilByAcceptation(String acceptationId) throws InterruptedException, ExecutionException;
}