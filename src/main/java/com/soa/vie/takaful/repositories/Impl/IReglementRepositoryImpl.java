package com.soa.vie.takaful.repositories.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.Reglement;
import com.soa.vie.takaful.repositories.IReglementRepositoryCostum;

@Repository
public class IReglementRepositoryImpl implements IReglementRepositoryCostum{

    @Override
    public Page<Reglement> findReglements(Pageable pageable) {
        //StringBuilder queryBuilder="SELECT  ";
        throw new UnsupportedOperationException("Unimplemented method 'findReglements'");
    }
    
}
