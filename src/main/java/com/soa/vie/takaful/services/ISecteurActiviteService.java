package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.SecteurActiviteModelResponse;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateSecteurActivite;

public interface ISecteurActiviteService {

	public SecteurActiviteModelResponse createSecteurActivite(CreateAndUpdateSecteurActivite secteur)
			throws InterruptedException, ExecutionException;

	public SecteurActiviteModelResponse updateSecteurActivite(String secteurId,
			CreateAndUpdateSecteurActivite secteurModel) throws InterruptedException, ExecutionException;

	public Page<SecteurActiviteModelResponse> getsecteurActivites(int page, int limit, String sort, String direction)
			throws InterruptedException, ExecutionException;

	public SecteurActiviteModelResponse getsecteurActiviteById(String id)
			throws InterruptedException, ExecutionException;

	public SecteurActiviteModelResponse getsecteurActiviteByLibelle(String libelle)
			throws InterruptedException, ExecutionException;

}
