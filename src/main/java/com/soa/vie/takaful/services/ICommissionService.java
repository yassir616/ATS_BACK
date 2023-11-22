package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.CommissionResponseModel;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateCommission;

public interface ICommissionService {

	public CommissionResponseModel createCommission(CreateAndUpdateCommission model) throws InterruptedException, ExecutionException;

	public CommissionResponseModel updateCommission(String commissionId, CreateAndUpdateCommission model) throws InterruptedException, ExecutionException;

	public Page<CommissionResponseModel> getCommissions(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

}
