package com.soa.vie.takaful.repositories;


import com.soa.vie.takaful.entitymodels.PrestationRachatTotal;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@JaversSpringDataAuditable
public interface IPrestationRachatTotalRepository extends PagingAndSortingRepository<PrestationRachatTotal , String> {
    public List<PrestationRachatTotal> findByContratId(String contratId);

}
