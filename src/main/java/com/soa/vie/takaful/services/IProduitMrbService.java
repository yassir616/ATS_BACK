package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateProduitMRB;
import com.soa.vie.takaful.requestmodels.UpdateProductMRB;
import com.soa.vie.takaful.responsemodels.ProduitMrbModelResponse;

import org.springframework.data.domain.Page;

public interface IProduitMrbService {
	public ProduitMrbModelResponse createProduitMrb(CreateAndUpdateProduitMRB model) throws InterruptedException, ExecutionException;

	public Page<ProduitMrbModelResponse> getAllProductMrb(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

	public ProduitMrbModelResponse updateProduitMrb(String id, UpdateProductMRB model) throws InterruptedException, ExecutionException;
}
