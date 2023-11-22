package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.PrestationSinistreMrbModelResponse;
import org.springframework.data.domain.Page;
import com.soa.vie.takaful.entitymodels.PrestationSinistreMrb;
import com.soa.vie.takaful.entitymodels.SinistreMrb;
import com.soa.vie.takaful.requestmodels.CreateSinistre;
import com.soa.vie.takaful.requestmodels.UpdateSinistreMrb;

public interface ISinistreMrbService {

	public PrestationSinistreMrbModelResponse createSinistre(CreateSinistre requestModel)
			throws InterruptedException, ExecutionException;

	public List<PrestationSinistreMrbModelResponse> getSinistrebyContrat(String contratId)
			throws InterruptedException, ExecutionException;

	public PrestationSinistreMrbModelResponse updateSinistre(UpdateSinistreMrb model, String id)
			throws InterruptedException, ExecutionException;

	public void deleteSinistre(String id);

	public Page<PrestationSinistreMrb> setStatut(String id, String statut, int page, int limit, String motif)
			throws InterruptedException, ExecutionException;

	public SinistreMrb getDataFromSinistre(SinistreMrb sinistre);
}
