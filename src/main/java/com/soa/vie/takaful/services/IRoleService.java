package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Privilege;

import com.soa.vie.takaful.entitymodels.Role;
import com.soa.vie.takaful.responsemodels.RoleModelResponse;
import org.springframework.data.domain.Page;

public interface IRoleService {

    public Role createRoleIfNotExist(String roleName, List<Privilege> privileges) throws InterruptedException, ExecutionException;

    public RoleModelResponse updateRole(String roleId, String roleName, List<Privilege> privileges) throws InterruptedException, ExecutionException;

    public Page<RoleModelResponse> getAllRoles(int page, int size, String sort, String order) throws InterruptedException, ExecutionException;

}