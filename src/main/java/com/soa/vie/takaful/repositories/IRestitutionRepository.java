package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Restitution;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface IRestitutionRepository extends PagingAndSortingRepository<Restitution, String> {

    public Optional<Restitution> findByLibelle(String libelle);

}