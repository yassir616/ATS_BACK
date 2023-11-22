package com.soa.vie.takaful.repositories;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import org.springframework.stereotype.Repository;

import com.soa.vie.takaful.entitymodels.Exclusion;
@Repository
@JaversSpringDataAuditable
public interface IExclusionRepository extends PagingAndSortingRepository<Exclusion, String> {
    public Exclusion findByExclusionNom(String exclusion);
   
    @Query(
            value = " select * from exclusion e where e.famille like ?%",
            countQuery = " SELECT count(*) from exclusion u WHERE u.famille like ?%",
            nativeQuery = true)
        Page<Exclusion> findByFamille(Pageable pageable, String famille);

}
