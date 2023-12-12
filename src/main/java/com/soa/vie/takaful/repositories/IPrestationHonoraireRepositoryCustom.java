package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.entitymodels.Reglement;
import com.soa.vie.takaful.responsemodels.PrestationHonoraireResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPrestationHonoraireRepositoryCustom {

    public List<PrestationHonoraireResponseDTO> listerPrestationHonorairesByReglementId(String reglementId);
}
