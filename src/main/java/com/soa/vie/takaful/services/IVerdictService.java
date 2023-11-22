package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.VerdictModelResponse;

public interface IVerdictService {

    public List<VerdictModelResponse> getVerdicts(String type) throws InterruptedException, ExecutionException;
    
}