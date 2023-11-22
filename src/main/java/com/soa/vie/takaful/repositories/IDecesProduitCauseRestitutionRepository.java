package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.DecesProduitCauseRestitution;
@Repository
@JaversSpringDataAuditable
public interface IDecesProduitCauseRestitutionRepository extends PagingAndSortingRepository<DecesProduitCauseRestitution, String> {

}
