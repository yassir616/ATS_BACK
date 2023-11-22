package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateOptionAssurance;
import com.soa.vie.takaful.responsemodels.OptionAssuranceModelResponse;

import org.springframework.data.domain.Page;

public interface IOptionAssuranceService {
	
	    public OptionAssuranceModelResponse createOptionAssurance(CreateAndUpdateOptionAssurance optionAssurance) throws InterruptedException, ExecutionException;
 
		public OptionAssuranceModelResponse updateOptionAssurance(String optionId, CreateAndUpdateOptionAssurance optionAssurance) throws InterruptedException, ExecutionException;

		public Page<OptionAssuranceModelResponse> getOptionAssuarnces(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

		public OptionAssuranceModelResponse getOptionAssuranceById(String id) throws InterruptedException, ExecutionException;
}
