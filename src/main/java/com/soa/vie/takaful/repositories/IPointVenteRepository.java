package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.PointVente;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface IPointVenteRepository extends PagingAndSortingRepository<PointVente, String> {
	public PointVente findByAbb(String abb);

	Optional<PointVente> findByCodeInterne(String code);
}
