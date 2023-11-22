package com.soa.vie.takaful.repositories.autoincrementhelpers;

import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueLotEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumIdGeneratedValueLotRepository
                extends PagingAndSortingRepository<CostumIdGeneratedValueLotEntity, String> {

}

