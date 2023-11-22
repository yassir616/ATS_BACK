package com.soa.vie.takaful.services;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePrestationRachatTotal;
import com.soa.vie.takaful.responsemodels.PrestationRachatTotalModelResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IPrestationRachatTotalService {
    public PrestationRachatTotalModelResponse createPrestationRachatTotal(CreateAndUpdatePrestationRachatTotal requestModel) throws InterruptedException, ExecutionException;
    public List<PrestationRachatTotalModelResponse> getPrestationsRachatTotalbyContrat(String contratId) throws InterruptedException, ExecutionException;
    public PrestationRachatTotalModelResponse updatePrestationRachatTotal(CreateAndUpdatePrestationRachatTotal model, String id) throws InterruptedException, ExecutionException;
    public void deletePrestationRachatTotal(String id) throws InterruptedException, ExecutionException;
}
