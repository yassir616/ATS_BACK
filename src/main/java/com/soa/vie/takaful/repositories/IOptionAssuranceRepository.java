package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.OptionAssurance;
@Repository
@JaversSpringDataAuditable
public interface IOptionAssuranceRepository extends PagingAndSortingRepository<OptionAssurance, String> {

}
