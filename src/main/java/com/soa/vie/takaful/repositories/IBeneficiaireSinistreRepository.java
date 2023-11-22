package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.BeneficiaireSinistre;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IBeneficiaireSinistreRepository extends PagingAndSortingRepository<BeneficiaireSinistre,String>{

}