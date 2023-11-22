package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.ExclusionResponseModel;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateExclusion;

public interface IExclusionService {
	
	    public ExclusionResponseModel createExclusion(CreateAndUpdateExclusion model) throws InterruptedException, ExecutionException;
	 
		public ExclusionResponseModel updateExclusion(String id, CreateAndUpdateExclusion model) throws InterruptedException, ExecutionException;

		public Page<ExclusionResponseModel> getExclusions(int page, int limit, String sort, String direction, String famille) throws InterruptedException, ExecutionException;
	   

}
