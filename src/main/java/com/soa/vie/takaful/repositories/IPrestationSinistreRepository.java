package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface IPrestationSinistreRepository extends PagingAndSortingRepository<PrestationSinistre, String>,IPrestationSinistreRepositoryCustom {

}
