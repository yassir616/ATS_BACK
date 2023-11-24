package com.soa.vie.takaful.repositories;

import java.util.Date;
import java.util.List;

import com.soa.vie.takaful.entitymodels.Cotisation;

public interface ICotisationRepositoryCustom {
public List<Cotisation>  recupererParIds(Date startDate, Date endDate, String partenaireID, String produitID);

} 