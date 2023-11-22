package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Avenant;
import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.TypeAvenant;
import com.soa.vie.takaful.responsemodels.AvenantModelResponse;

public interface IAvenantService {

    public List<AvenantModelResponse> getByContratId(String id) throws InterruptedException, ExecutionException;

    public Avenant createAvenant(Contract contract, Integer numeroAvenant, TypeAvenant type)
            throws InterruptedException, ExecutionException;

    public Avenant createAvenantChangementAdresse(Contract contract, TypeAvenant type);
}