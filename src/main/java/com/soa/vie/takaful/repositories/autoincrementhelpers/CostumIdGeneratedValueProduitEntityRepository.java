package com.soa.vie.takaful.repositories.autoincrementhelpers;

import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueDecesEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumIdGeneratedValueProduitEntityRepository
                extends PagingAndSortingRepository<CostumIdGeneratedValueDecesEntity, String> {

}
