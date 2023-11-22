package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.PrestationSinistreModelResponse;
import org.springframework.data.domain.Page;

import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.requestmodels.CreateSinistre;
import com.soa.vie.takaful.requestmodels.UpdateSinistre;

public interface ISinistreService {

	public PrestationSinistreModelResponse createSinistre(CreateSinistre requestModel)
			throws InterruptedException, ExecutionException;

	public List<PrestationSinistreModelResponse> getSinistrebyContrat(String contratId)
			throws InterruptedException, ExecutionException;

	public PrestationSinistreModelResponse updateSinistre(UpdateSinistre model, String id)
			throws InterruptedException, ExecutionException;

	public void deleteSinistre(String id) throws InterruptedException, ExecutionException;

	public Page<PrestationSinistreModelResponse> setStatut(String id, String statut, int page, int limit, String motif)
			throws InterruptedException, ExecutionException;

	public List<PrestationSinistre> searchSinistre(String searchby, String searchfor);

	public void notifLettreRelance(String numContrat, String pointVente);

}