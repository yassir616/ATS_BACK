package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationExamensModelResponse;

public interface IAcceptationExamensService {

    public AcceptationExamensModelResponse createAcceptationExamens (CreateallAcceptationTestKindModelRequest model) throws InterruptedException, ExecutionException;


    public List<AcceptationExamensModelResponse> acceptationsExamensByAcceptation(String acceptation) throws InterruptedException, ExecutionException;
}