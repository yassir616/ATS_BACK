package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdateCatAndSubCatRequestModel;

import com.soa.vie.takaful.responsemodels.CategorieModelResponse;
import org.springframework.data.domain.Page;

public interface ICategorieService {

    public CategorieModelResponse createCategorie(CreateUpdateCatAndSubCatRequestModel requestModel) throws InterruptedException, ExecutionException;

    public CategorieModelResponse updateCategorie(String id, CreateUpdateCatAndSubCatRequestModel requestModel) throws InterruptedException, ExecutionException;

    public Page<CategorieModelResponse> getCategories(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

}