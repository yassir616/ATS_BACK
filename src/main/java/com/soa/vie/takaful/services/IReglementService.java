package com.soa.vie.takaful.services;


import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateReglementRequest;
import com.soa.vie.takaful.responsemodels.ReglementResponseModel;

import org.springframework.data.domain.Page;

public interface IReglementService {
	   public ReglementResponseModel reglement (String idProduct, String type, CreateReglementRequest model) throws InterruptedException, ExecutionException;
	   public ReglementResponseModel reglementHonoraire (String ModeReglement,String Auxiliaire,String type, CreateReglementRequest model) throws InterruptedException, ExecutionException;
	   public Page<ReglementResponseModel> getReglements(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;
	   public List<ReglementResponseModel> reglementPrestation(String id, String statut) throws InterruptedException, ExecutionException;
}
