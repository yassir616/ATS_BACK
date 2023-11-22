package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Risque;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IRisqueRepository extends PagingAndSortingRepository<Risque, String> {
    public Page<Risque> findAllRisqueByTheme(Pageable pageable, String theme);

}