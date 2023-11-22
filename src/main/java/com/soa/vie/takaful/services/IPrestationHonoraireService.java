package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdatePrestationHonoraireRequestModel;
import com.soa.vie.takaful.responsemodels.PrestationHonoraireResponseModel;

public interface IPrestationHonoraireService {

    public PrestationHonoraireResponseModel createPrestationHonoraire(
            CreateAndUpdatePrestationHonoraireRequestModel model) throws InterruptedException, ExecutionException;

    public List<PrestationHonoraireResponseModel> getPrestationHonoraire(String typeAuxiliaire, String status,
            String produit, String auxiliaire, String contrat, String participant) throws InterruptedException, ExecutionException;

    public PrestationHonoraireResponseModel updateStatusPrestation(String id, String p);
    public void updateMontantPrestation(String idDetail,String numeroSinistre,Float montantHonoraire,String typeFiscal);

} 