package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationReassurance;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IAcceptationReassuranceRepository extends PagingAndSortingRepository<AcceptationReassurance, String> {
    public List<AcceptationReassurance> findByAcceptation(Acceptation acceptation);

}