package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.AcceptationEtape;

import com.soa.vie.takaful.entitymodels.AcceptationReassurance;
import com.soa.vie.takaful.entitymodels.AcceptationTestMedical;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IAcceptationEtapeRepository extends PagingAndSortingRepository<AcceptationEtape, String> {


    public AcceptationEtape findByEtapeAndAcceptationTestMedical(String etape,AcceptationTestMedical acceptation);
    public AcceptationEtape findByEtapeAndAcceptationReassurance(String etape,AcceptationReassurance acceptation);

}