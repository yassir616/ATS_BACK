package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.HonoraireModelResponse;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateHonoraire;

public interface IHonoraireService {
	
	public HonoraireModelResponse createHonoraire (CreateAndUpdateHonoraire model) throws InterruptedException, ExecutionException;

	public HonoraireModelResponse updateHonoraire(String id, CreateAndUpdateHonoraire model) throws InterruptedException, ExecutionException;

	public Page<HonoraireModelResponse> getHonoraires(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

}
