package com.soa.vie.takaful.repositories;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.Privilege;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IPrivilegeRepository extends PagingAndSortingRepository<Privilege, String> {

    public Optional<Privilege> findPrivilegeByName(String name);

}