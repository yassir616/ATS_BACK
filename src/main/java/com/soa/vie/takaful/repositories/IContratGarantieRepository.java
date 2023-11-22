package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.ContratGarantie;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IContratGarantieRepository extends PagingAndSortingRepository<ContratGarantie , String> {
    
}