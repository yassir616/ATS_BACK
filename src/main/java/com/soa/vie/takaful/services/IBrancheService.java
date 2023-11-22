package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateBranche;


import com.soa.vie.takaful.responsemodels.BrancheModelResponse;
import org.springframework.data.domain.Page;

public interface IBrancheService {

    public BrancheModelResponse createBranche(CreateAndUpdateBranche branche) throws InterruptedException, ExecutionException;

    public BrancheModelResponse updateBranche(String brancheId, CreateAndUpdateBranche brancheModel) throws InterruptedException, ExecutionException;

    public Page<BrancheModelResponse> getBranches(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

    public BrancheModelResponse getBrancheById(String id) throws InterruptedException, ExecutionException;

}