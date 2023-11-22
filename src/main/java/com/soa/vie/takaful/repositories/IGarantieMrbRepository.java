package com.soa.vie.takaful.repositories;


import com.soa.vie.takaful.entitymodels.GarantieMrb;


import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IGarantieMrbRepository extends PagingAndSortingRepository<GarantieMrb , String> {
    
}