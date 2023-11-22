package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.Periodicite;

@Repository
@JaversSpringDataAuditable
public interface IPeriodiciteRepository extends PagingAndSortingRepository<Periodicite, String> {

	public Optional<Periodicite> findByAbb(String abb);
	public Optional<Periodicite>  findByLibelle(String libelle);

}
