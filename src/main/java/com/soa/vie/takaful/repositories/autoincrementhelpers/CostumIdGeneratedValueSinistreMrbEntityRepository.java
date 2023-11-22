package com.soa.vie.takaful.repositories.autoincrementhelpers;

import com.soa.vie.takaful.entitymodels.autoIncrementhelpers.CostumIdGeneratedValueSinistreMrbEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumIdGeneratedValueSinistreMrbEntityRepository
        extends PagingAndSortingRepository<CostumIdGeneratedValueSinistreMrbEntity, String> {

}
