package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Branche;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IBrancheRepository extends PagingAndSortingRepository<Branche, String> {

    public Optional<Branche> findByCode(String code);

	public List<Branche> findByIcon(String icon);

}