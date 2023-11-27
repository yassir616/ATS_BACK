package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Cotisation;
import com.soa.vie.takaful.requestmodels.EmissionGroupeRequestModel;

public interface ICotisationRepositoryCustom {
public List<Cotisation>  recupererEmissionGroupeParCriteres(EmissionGroupeRequestModel requestModel);

} 