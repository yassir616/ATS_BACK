package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.responsemodels.PrestationHonoraireResponseDTO;
import com.soa.vie.takaful.responsemodels.PrestationSinistreResponseDTO;

import java.util.List;

public interface IPrestationSinistreRepositoryCustom {
    public List<PrestationSinistreResponseDTO> listerPrestationSinistresByReglementId(String reglementId);

}
