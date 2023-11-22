package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateBeneficiaireEnDeces;
import com.soa.vie.takaful.responsemodels.BeneficiaireEnDecesResponseModel;

import org.springframework.data.domain.Page;


public interface IBeneficiaireEnDecesService {
    BeneficiaireEnDecesResponseModel createBeneficiaireEnDeces(CreateAndUpdateBeneficiaireEnDeces model) throws InterruptedException, ExecutionException;


    BeneficiaireEnDecesResponseModel updateBeneficiaireEnDeces(String id ,CreateAndUpdateBeneficiaireEnDeces model) throws InterruptedException, ExecutionException;

    void deleteBeneficiaireEnDeces(String id) throws InterruptedException, ExecutionException;

    Page<BeneficiaireEnDecesResponseModel> getBeneficiairesEnDeces(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

    BeneficiaireEnDecesResponseModel getBeneficiaireEnDecesById(String id) throws InterruptedException, ExecutionException;


}
