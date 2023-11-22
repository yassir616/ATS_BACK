package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.UpdatePriviligeRequest;
import com.soa.vie.takaful.responsemodels.PrivilegeModelResponse;

public interface IPrivilegeService {

    public PrivilegeModelResponse createPrivilegeIfNotFound(String privilegeName) throws InterruptedException, ExecutionException;

    public List<PrivilegeModelResponse> getAllPrivileges() throws InterruptedException, ExecutionException;

    public void deletePrivilege(String id);
    
	public PrivilegeModelResponse updatePrivilege(String privilegeId, UpdatePriviligeRequest privilegeModel) throws InterruptedException, ExecutionException;

}