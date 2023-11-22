package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.TypePointVenteModelResponse;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePointVente;

public interface ITypePointVenteService {
	
	public TypePointVenteModelResponse createTypePointVente(CreateAndUpdateTypePointVente model) throws InterruptedException, ExecutionException;

    public TypePointVenteModelResponse updateTypePointVente(CreateAndUpdateTypePointVente model, String typePointVenteId) throws InterruptedException, ExecutionException;

    public TypePointVenteModelResponse getTypePointVente(String typePointVenteId) throws InterruptedException, ExecutionException;

    public Page<TypePointVenteModelResponse> getTypesPointVente(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

}
