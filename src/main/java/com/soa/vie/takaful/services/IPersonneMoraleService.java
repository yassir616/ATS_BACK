package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePersonneMor;
import com.soa.vie.takaful.responsemodels.PersonneMoraleResponseModel;

import org.springframework.data.domain.Page;

public interface IPersonneMoraleService {

    public PersonneMoraleResponseModel createPersonneMorale(CreateAndUpdatePersonneMor p) throws InterruptedException, ExecutionException;

    public PersonneMoraleResponseModel updatePersonneMorale(String id, CreateAndUpdatePersonneMor p) throws InterruptedException, ExecutionException;

    public Page<PersonneMoraleResponseModel> getPersonneMorale(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

    public PersonneMoraleResponseModel getPersonneMorale(String patente) throws InterruptedException, ExecutionException;

    public void deleteParticipant(String id) throws InterruptedException, ExecutionException;
}