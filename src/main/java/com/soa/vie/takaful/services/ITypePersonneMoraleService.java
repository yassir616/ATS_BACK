package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.TypePersonneMoraleModelResponse;

public interface ITypePersonneMoraleService {

    public List<TypePersonneMoraleModelResponse> getTypesPersonneMorales()
            throws InterruptedException, ExecutionException;

    public TypePersonneMoraleModelResponse getTypePersonneMoraleByLibelle(String libelle)
            throws InterruptedException, ExecutionException;
}
