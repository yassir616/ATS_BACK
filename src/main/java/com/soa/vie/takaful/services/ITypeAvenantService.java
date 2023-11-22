package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateTypeAvenant;

import com.soa.vie.takaful.responsemodels.TypeAvenantResponseModel;
import org.springframework.data.domain.Page;

public interface ITypeAvenantService {
    public TypeAvenantResponseModel createTypeAvenant(CreateTypeAvenant model) throws InterruptedException, ExecutionException;
    public Page<TypeAvenantResponseModel> getTypeAvenants(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;
    public String getCodeById(String id) throws InterruptedException, ExecutionException;




}