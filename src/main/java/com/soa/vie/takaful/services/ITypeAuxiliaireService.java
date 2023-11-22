package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.TypeAuxiliaireModelResponse;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import javax.websocket.EncodeException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypeAuxiliaire;

public interface ITypeAuxiliaireService {
	
	    public TypeAuxiliaireModelResponse createTypeAuxiliaire(CreateAndUpdateTypeAuxiliaire model) throws InterruptedException, ExecutionException;

	    public TypeAuxiliaireModelResponse updateTypeAuxiliaire(String id, CreateAndUpdateTypeAuxiliaire model) throws InterruptedException, ExecutionException;

	    public Page<TypeAuxiliaireModelResponse> getTypeAuxiliaires(int page, int limit, String sort, String direction) throws InterruptedException, EncodeException;

}
