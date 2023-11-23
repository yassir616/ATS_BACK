package com.soa.vie.takaful.services;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.CotisationModelResponse;
import org.springframework.data.domain.Page;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.requestmodels.AnnulationCotisationRequest;
import com.soa.vie.takaful.requestmodels.CotisationRequestDTO;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateCotisation;
import com.soa.vie.takaful.requestmodels.CreateEmissionGlobale;

public interface ICotisationService {

	public Page<CotisationModelResponse> getCotisations(int page, int limit)
			throws InterruptedException, ExecutionException;

	public CotisationModelResponse createCotisation(CreateAndUpdateCotisation model)
			throws InterruptedException, ExecutionException;

	public List<CotisationModelResponse> getCotisationByContrat(String id) throws InterruptedException, ExecutionException;

	public List<CotisationModelResponse> getCotisationByContrats(String type)
			throws InterruptedException, ExecutionException;

	public List<String> productByContrats() throws InterruptedException, ExecutionException;

	public List<String> productByContrat() throws InterruptedException, ExecutionException;

	public CotisationModelResponse AnnulationCotisation(String id, AnnulationCotisationRequest model)
			throws InterruptedException, ExecutionException;

	public List<Cotisation> saveCotisation(List<Cotisation> cotisations)
			throws InterruptedException, ExecutionException;

	// public List<Cotisation> getEmissionGlobale(String partenaireId, String produitId, String startDate, String endDate)
	// 		throws InterruptedException, ExecutionException;
	
	public List<CotisationRequestDTO> getEmissionGlobale(String partenaireId, String produitId, String startDate, String endDate)
			throws InterruptedException, ExecutionException,ParseException;

	public CotisationModelResponse changeStatusCotisation(List<CotisationModelResponse> listCotisation,
			CreateEmissionGlobale createEmissionGlobale, String reference)
			throws InterruptedException, ExecutionException;

	public List<Cotisation> getCotisationsBynumeroLot(String numeroLot) throws InterruptedException, ExecutionException;

}
