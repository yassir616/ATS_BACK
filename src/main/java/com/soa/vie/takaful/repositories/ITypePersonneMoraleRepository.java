package com.soa.vie.takaful.repositories;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.TypePersonneMorale;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface ITypePersonneMoraleRepository extends PagingAndSortingRepository<TypePersonneMorale, String> {
    public Optional<TypePersonneMorale> findByLibelle(String libelle);

}