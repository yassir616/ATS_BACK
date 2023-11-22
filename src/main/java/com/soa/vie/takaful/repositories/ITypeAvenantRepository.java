package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.TypeAvenant;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@JaversSpringDataAuditable
public interface ITypeAvenantRepository extends PagingAndSortingRepository<TypeAvenant, String> {

    @Query(
        value = "select code as num from type_avenant where id = :id", 
        countQuery = "SELECT count(*) from type_avenant where id = :id ",
        nativeQuery = true)
    public String codeTypeAvenant(String id);
}