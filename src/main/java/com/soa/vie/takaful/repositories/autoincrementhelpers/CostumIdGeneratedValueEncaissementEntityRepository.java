package com.soa.vie.takaful.repositories.autoincrementhelpers;

import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueEncaissementEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumIdGeneratedValueEncaissementEntityRepository
        extends PagingAndSortingRepository<CostumIdGeneratedValueEncaissementEntity, String> {

}
