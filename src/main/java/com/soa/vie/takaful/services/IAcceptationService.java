package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.AcceptationModelResponse;

import org.springframework.data.domain.Page;

public interface IAcceptationService {

	public Page<AcceptationModelResponse> getAcceptations(int page, int limit, String sort, String direction)
			throws InterruptedException, ExecutionException;

	public Page<AcceptationModelResponse> searchAcceptation(int page, int limit, String sort, String direction,
			String searchBy, String searchFor) throws InterruptedException, ExecutionException, ExecutionException;

	public String getCodeByContratId(String id);

	public AcceptationModelResponse getAcceptationByContratId(String id);

}
