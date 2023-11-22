package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Verdict;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IVerdictRepository extends PagingAndSortingRepository<Verdict, String> {
    public List<Verdict> findByType(String type);
}