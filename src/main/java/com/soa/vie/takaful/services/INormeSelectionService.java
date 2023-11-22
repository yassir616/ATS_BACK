package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.NormeSelection;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateNormeSelection;
import com.soa.vie.takaful.requestmodels.UpdateNormeSelection;

import com.soa.vie.takaful.responsemodels.NormeSelectionResponseModel;
import org.springframework.data.domain.Page;

public interface INormeSelectionService {

    public NormeSelectionResponseModel createNormeSelection (CreateAndUpdateNormeSelection model) throws InterruptedException, ExecutionException, ExecutionException;
	 
    public NormeSelectionResponseModel updateNormeSelection(String id, UpdateNormeSelection model) throws InterruptedException, ExecutionException;

    public Page<NormeSelection> getNormeSelections(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;
	public List<NormeSelectionResponseModel> getNormeById(String productId) throws InterruptedException, ExecutionException;

    public NormeSelectionResponseModel getNorme(int age, int agem, float capital, float capitalm,String decesProduit) throws InterruptedException, ExecutionException;



}