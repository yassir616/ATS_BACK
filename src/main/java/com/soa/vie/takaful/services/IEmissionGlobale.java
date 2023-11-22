package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import org.springframework.data.domain.Page;

import com.soa.vie.takaful.entitymodels.EmissionGlobale;

public interface IEmissionGlobale {
	public Page<EmissionGlobale> getAllEmissionGlobale(int pageNumber,int pageSize,String sortCol,Boolean direction) throws InterruptedException, ExecutionException;
	public EmissionGlobale findByNumeroLot(String numeroLot) throws InterruptedException,ExecutionException;

}
