
package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Beneficiare;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IBeneficiareRepository extends PagingAndSortingRepository<Beneficiare, String> {

}
