package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.TypePrestationProduitModelResponse;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePrestationProduit;

public interface ITypePrestationProduitService {
	
	   public TypePrestationProduitModelResponse createTypePrestationProduit(CreateAndUpdateTypePrestationProduit model) throws InterruptedException, ExecutionException;

	    public TypePrestationProduitModelResponse updateTypePrestationProduit(String id, CreateAndUpdateTypePrestationProduit model) throws InterruptedException, ExecutionException;

	    public Page<TypePrestationProduitModelResponse> getTypePrestationProduits(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;


}
