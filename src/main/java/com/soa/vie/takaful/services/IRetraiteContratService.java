package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdateRetraiteContrat;
import com.soa.vie.takaful.requestmodels.UpdateRetraiteContratStatusModel;
import com.soa.vie.takaful.responsemodels.RetraiteContratResponseModel;
import com.soa.vie.takaful.util.ContratStatus;

import org.springframework.data.domain.Page;

public interface IRetraiteContratService {

    RetraiteContratResponseModel createRetraitContrat(CreateUpdateRetraiteContrat model) throws InterruptedException, ExecutionException;

    RetraiteContratResponseModel updateRetraitContrat(String id, CreateUpdateRetraiteContrat model) throws InterruptedException, ExecutionException;

    Page<RetraiteContratResponseModel> getRetraitContrats(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

    RetraiteContratResponseModel getRetraitContratById(String id) throws InterruptedException, ExecutionException;

    RetraiteContratResponseModel getRetraitContratByNumeroContrat(String numeroContrat) throws InterruptedException, ExecutionException;

    Page<RetraiteContratResponseModel> getRetraitContratsByStatus(ContratStatus status, int page, int limit) throws InterruptedException, ExecutionException;

    Page<RetraiteContratResponseModel> searchContrat(int page, int limit, String searchBy, String searchFor) throws InterruptedException, ExecutionException;

    RetraiteContratResponseModel updateRetraitContratStatus(String id, UpdateRetraiteContratStatusModel model) throws InterruptedException, ExecutionException;

}
