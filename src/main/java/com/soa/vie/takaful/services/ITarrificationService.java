package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.TarrificationResponseModel;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTarrification;

public interface ITarrificationService {
	
	public TarrificationResponseModel createTarrification(CreateAndUpdateTarrification model) throws InterruptedException, ExecutionException;

    public TarrificationResponseModel updateTarrification(CreateAndUpdateTarrification model,
                                                          String tarrificationId) throws InterruptedException, ExecutionException;

    public TarrificationResponseModel getTarrification(int duree, int dureem, int age, int agem, float capital, float capitalm, float differe, float differem, String typeProduit  , String decesProduit) throws InterruptedException, ExecutionException;

    public Page<TarrificationResponseModel> getTarrifications(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

}
