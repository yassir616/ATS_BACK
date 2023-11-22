package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.PoolInvestissment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPoolInvestissmentRepository extends PagingAndSortingRepository<PoolInvestissment, String> {
}
