package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.DecesProduitResponseModel;
import com.soa.vie.takaful.responsemodels.ProduitModelResponse;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.DecesProduit;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateDecesProduit;
import com.soa.vie.takaful.requestmodels.UpdateDecesProduit;

public interface IDecesProduitService {
	    public ProduitModelResponse createDecesProduit(CreateAndUpdateDecesProduit model) throws InterruptedException, ExecutionException;
	 
		public ProduitModelResponse updateDecesProduit(String id, UpdateDecesProduit model) throws InterruptedException, ExecutionException;

		public Page<DecesProduitResponseModel> getDecesProduits(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

		public DecesProduit getDecesProduitById(String id) throws InterruptedException, ExecutionException ;
		
		public Optional<DecesProduit> getDecesProduitByCode(String code);
		

}
