package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.ProduitModelResponse;

public interface IProduitService {

    public ProduitModelResponse getProduitByCode(String code)
            throws InterruptedException, ExecutionException;

}
