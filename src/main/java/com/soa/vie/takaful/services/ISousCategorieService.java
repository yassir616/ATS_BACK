package com.soa.vie.takaful.services;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdateCatAndSubCatRequestModel;

import com.soa.vie.takaful.responsemodels.SousCategorieModelResponse;
import org.springframework.data.domain.Page;

public interface ISousCategorieService {

    public SousCategorieModelResponse createSousCategorie(CreateUpdateCatAndSubCatRequestModel requestModel) throws InterruptedException, ExecutionException;

    public SousCategorieModelResponse updateSousCategorie(String id, CreateUpdateCatAndSubCatRequestModel requestModel) throws InterruptedException, ExecutionException;

    public Page<SousCategorieModelResponse> getSousCategories(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

}