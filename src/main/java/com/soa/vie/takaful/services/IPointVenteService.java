package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.PointVenteResponseModel;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePointVente;

public interface IPointVenteService {
	public PointVenteResponseModel createPointVente(CreateAndUpdatePointVente model) throws InterruptedException, ExecutionException;

    public PointVenteResponseModel updatePointVente(CreateAndUpdatePointVente model,String pointVenteId) throws InterruptedException, ExecutionException;

    public PointVenteResponseModel getPointVenteById(String pointVenteId) throws InterruptedException, ExecutionException;

    public Page<PointVenteResponseModel> getPointVentes(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

    public PointVenteResponseModel getPointVenteByCodeInterne(String code) throws InterruptedException, ExecutionException;
    
   




}
