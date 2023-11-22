package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.DecesProduitCauseRestitutionModelResponse;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateDecesProduitCauseRestitution;

public interface IDecesProdCauseRestService {
	
	    public DecesProduitCauseRestitutionModelResponse createDecesProduitCauseRestitution(CreateAndUpdateDecesProduitCauseRestitution model) throws InterruptedException, ExecutionException;

	    public DecesProduitCauseRestitutionModelResponse updateDecesProduitCauseRestitution(String id, CreateAndUpdateDecesProduitCauseRestitution model) throws InterruptedException, ExecutionException;

	    public Page<DecesProduitCauseRestitutionModelResponse> getDecesProduitCauseRestitutions(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException, ExecutionException;

}
