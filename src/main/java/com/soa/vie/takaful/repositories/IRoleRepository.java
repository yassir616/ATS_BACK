package com.soa.vie.takaful.repositories;

import com.soa.vie.takaful.entitymodels.Role;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface IRoleRepository extends PagingAndSortingRepository<Role, String> {

    public Role findRoleByName(String name);

}