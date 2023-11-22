package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationExaminateur;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IAcceptationExaminateurRepository extends PagingAndSortingRepository<AcceptationExaminateur, String>{

    public List<AcceptationExaminateur> findByAcceptation(Acceptation acceptation);

}