package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePersonnePhy;

import com.soa.vie.takaful.responsemodels.PersonnePhysiqueResponseModel;
import org.springframework.data.domain.Page;

public interface IPersonnePhysiqueService {

    public PersonnePhysiqueResponseModel createPersonnePhysique(CreateAndUpdatePersonnePhy personnePhy) throws InterruptedException, ExecutionException;

    public PersonnePhysiqueResponseModel updatePersonnePhysique(String id, CreateAndUpdatePersonnePhy personnePhy) throws InterruptedException, ExecutionException;

    public Page<PersonnePhysiqueResponseModel> getPersonnePhysique(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

    public PersonnePhysiqueResponseModel getPersonnePhysique(String id) throws InterruptedException, ExecutionException;

    public void deleteParticipant(String id) throws InterruptedException, ExecutionException;

    public void personneExist(String cin) throws InterruptedException, ExecutionException;

    public void ribExist(String rib) throws InterruptedException, ExecutionException;
}