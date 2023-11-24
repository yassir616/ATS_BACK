package com.soa.vie.takaful.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soa.vie.takaful.entitymodels.Reglement;

public interface IReglementRepositoryCostum {
    
    Page<Reglement> findReglements(Pageable pageable);
}
