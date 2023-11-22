package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.CompteBancaireResponseModel;
import org.springframework.data.domain.Page;


public interface ICompteBancaireService {
	 public Page<CompteBancaireResponseModel> getCompteBancaire(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

	public CompteBancaireResponseModel getCompteBancaireById(String idCpt) throws InterruptedException, ExecutionException;

}
