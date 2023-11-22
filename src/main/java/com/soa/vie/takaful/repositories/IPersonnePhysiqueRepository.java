package com.soa.vie.takaful.repositories;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.PersonnePhysique;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IPersonnePhysiqueRepository extends PagingAndSortingRepository<PersonnePhysique,String>{

    public Optional<PersonnePhysique> findByCin(String cin);
    public Optional<PersonnePhysique> findByRib(String rib);
    // public Optional<PersonnePhysique> findByRib(String user, String rib);
}