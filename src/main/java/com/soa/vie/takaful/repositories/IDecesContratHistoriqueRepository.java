package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.soa.vie.takaful.entitymodels.DecesContratHistorique;

@JaversSpringDataAuditable
public interface IDecesContratHistoriqueRepository extends PagingAndSortingRepository<DecesContratHistorique, String> {

}
