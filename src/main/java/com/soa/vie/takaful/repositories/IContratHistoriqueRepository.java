package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.soa.vie.takaful.entitymodels.ContratHistorique;

@JaversSpringDataAuditable
public interface IContratHistoriqueRepository extends PagingAndSortingRepository<ContratHistorique, String>{

}
