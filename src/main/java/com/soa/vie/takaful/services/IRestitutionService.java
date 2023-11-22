package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateRestitution;
import com.soa.vie.takaful.responsemodels.RestitutionResponseModel;
import org.springframework.data.domain.Page;

public interface IRestitutionService  {

    public RestitutionResponseModel createRestitution (CreateAndUpdateRestitution restitution) throws InterruptedException, ExecutionException;

	public RestitutionResponseModel updateRestitution(String restitutionId, CreateAndUpdateRestitution restitutionModel) throws InterruptedException, ExecutionException;

	public Page<RestitutionResponseModel> getRestitution(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

	public RestitutionResponseModel getRestitutionById(String id) throws InterruptedException, ExecutionException;

}