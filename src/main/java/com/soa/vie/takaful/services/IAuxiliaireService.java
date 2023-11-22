package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.AuxiliaireModelResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateAuxiliaire;


public interface IAuxiliaireService {
	
	public AuxiliaireModelResponse createAuxiliaire (CreateAndUpdateAuxiliaire model) throws InterruptedException, ExecutionException;

	public AuxiliaireModelResponse updateAuxiliaire(String id, CreateAndUpdateAuxiliaire model) throws InterruptedException, ExecutionException;

	public AuxiliaireModelResponse getAuxiliaireById(String id) throws InterruptedException, ExecutionException;

	public Page<AuxiliaireModelResponse> getAuxiliaires(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;
	
	public List<AuxiliaireModelResponse> getAuxiliairebytype(String typeAuxiliaire) throws InterruptedException, ExecutionException;

	public List<Auxiliaire> getAuxiliairebystatut() throws InterruptedException, ExecutionException;

}
