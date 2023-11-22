package com.soa.vie.takaful.repositories;


import com.soa.vie.takaful.entitymodels.Partenaire;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface IPartenaireRepository extends PagingAndSortingRepository<Partenaire, String> {

    public Optional<Partenaire> findByCode(String code);

}