package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ejb.EJBException;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationExaminateurModelResponse;


public interface IAcceptationExaminateurService {

    public AcceptationExaminateurModelResponse createAcceptationExaminateur (CreateallAcceptationTestKindModelRequest model) throws InterruptedException, EJBException, ExecutionException;
    public List<AcceptationExaminateurModelResponse> acceptationExaminateurByAcceptation(String acceptationId) throws InterruptedException, ExecutionException;

}