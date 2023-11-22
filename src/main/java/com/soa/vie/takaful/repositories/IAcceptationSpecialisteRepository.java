package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationSpecialiste;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IAcceptationSpecialisteRepository extends PagingAndSortingRepository<AcceptationSpecialiste, String> {

   public List<AcceptationSpecialiste> findByAcceptation(Acceptation acceptation);

}