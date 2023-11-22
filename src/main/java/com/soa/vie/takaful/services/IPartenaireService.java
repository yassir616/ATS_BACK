package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePartenaire;
import com.soa.vie.takaful.responsemodels.PartenaireResponseModel;

import org.springframework.data.domain.Page;

public interface IPartenaireService {
	
	    public PartenaireResponseModel createPartenaire(CreateAndUpdatePartenaire partenaire) throws InterruptedException, ExecutionException;
 
		public PartenaireResponseModel updatePartenaire(String partenaireId, CreateAndUpdatePartenaire partenaireModel) throws InterruptedException, ExecutionException;

		public Page<PartenaireResponseModel> getPartenaires(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

		public PartenaireResponseModel getPartenaireById(String id) throws InterruptedException, ExecutionException;

		public PartenaireResponseModel getPartenaireByCode(String code) throws InterruptedException, ExecutionException;

}
