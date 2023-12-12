package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Reglement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReglementRepositoryCustom {
    Page<Reglement> listerReglements(Pageable pageable);
}
