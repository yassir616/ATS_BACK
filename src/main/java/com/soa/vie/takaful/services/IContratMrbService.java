package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateContratMrb;
import com.soa.vie.takaful.requestmodels.CreateContratMrb;

import com.soa.vie.takaful.responsemodels.ContratMrbModelResponse;
import org.springframework.data.domain.Page;

public interface IContratMrbService {

    public ContratMrbModelResponse createContratMrb(CreateAndUpdateContratMrb modelContratMrb) throws InterruptedException, ExecutionException;

    public ContratMrbModelResponse updateContratMrb(String id, CreateContratMrb modelContratMrb) throws InterruptedException, ExecutionException;

    public Page<ContratMrbModelResponse> getContratMrb(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

    public Page<ContratMrbModelResponse> searchContratMrb(int page, int limit, String searchBy, String searchFor) throws InterruptedException, ExecutionException;

}