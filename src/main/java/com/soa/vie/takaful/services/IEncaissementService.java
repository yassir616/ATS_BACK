package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Encaissement;
import com.soa.vie.takaful.requestmodels.BordereauEncaissementRequestModel;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateEncaissement;
import com.soa.vie.takaful.requestmodels.EncaissementGroupeRequestModel;
import com.soa.vie.takaful.responsemodels.EncaissementResponseModel;

public interface IEncaissementService {
	public EncaissementResponseModel createEncaissement(CreateAndUpdateEncaissement model)
			throws InterruptedException, ExecutionException;

	public List<EncaissementResponseModel> encaissementByCotisation(String id)
			throws InterruptedException, ExecutionException;

	public void EncaissementLot(EncaissementGroupeRequestModel model) throws InterruptedException, ExecutionException;

	public List<Encaissement> genererBordereauEncaissement(String partenaireId,String dateEncaissement,String compteBancaireId) 
			throws InterruptedException, ExecutionException;

	public List<BordereauEncaissementRequestModel> getAllBordereaux() throws InterruptedException, ExecutionException;

}
