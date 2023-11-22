package com.soa.vie.takaful.services;

import com.soa.vie.takaful.responsemodels.TarificationMRBResponseModel;
import org.springframework.data.domain.Page;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.ProduitMrb;

public interface ITarificationMrbService {

    public Page<TarificationMRBResponseModel> getTarificationMrbById(ProduitMrb id, int page, int limit) throws InterruptedException, ExecutionException;

    public TarificationMRBResponseModel getTarrification(//float valeurMax, 
           //String nature,
            String produit);

}
