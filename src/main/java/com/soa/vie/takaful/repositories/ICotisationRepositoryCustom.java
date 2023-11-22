package com.soa.vie.takaful.repositories;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Cotisation;

public interface ICotisationRepositoryCustom {
public List<Cotisation> recupererParIds(List<String> cotisationIds) throws InterruptedException, ExecutionException;
    
} 