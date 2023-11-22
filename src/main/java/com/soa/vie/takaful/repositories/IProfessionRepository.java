package com.soa.vie.takaful.repositories;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.Profession;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IProfessionRepository extends PagingAndSortingRepository<Profession, String> {
    public Optional<Profession> findByLibelle(String libelle);
}