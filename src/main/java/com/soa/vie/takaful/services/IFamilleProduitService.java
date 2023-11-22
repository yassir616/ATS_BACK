package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.FamilleProduitResponseModel;

import org.springframework.data.domain.Page;

public interface IFamilleProduitService {

    public String getIdFamilleProduct (String name) throws InterruptedException, ExecutionException;
    public Page<FamilleProduitResponseModel> getFamilleProduits(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;
    
}
