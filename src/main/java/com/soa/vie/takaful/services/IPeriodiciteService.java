package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePeriodicite;
import com.soa.vie.takaful.responsemodels.PeriodiciteResponseModel;

import org.springframework.data.domain.Page;

public interface IPeriodiciteService {
	public PeriodiciteResponseModel createPeriodicite(CreateAndUpdatePeriodicite model)
			throws InterruptedException, ExecutionException;

	public PeriodiciteResponseModel updatePeriodicite(String id, CreateAndUpdatePeriodicite model)
			throws InterruptedException, ExecutionException;

	public Page<PeriodiciteResponseModel> getPeriodicites(int page, int limit, String sort, String direction)
			throws InterruptedException, ExecutionException;

	public PeriodiciteResponseModel getPeriodiciteByAbb(String abb) throws InterruptedException, ExecutionException;
}
