package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.UpdatePriviligeRequest;
import com.soa.vie.takaful.responsemodels.PrivilegeModelResponse;
import com.soa.vie.takaful.services.IPrivilegeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class PrivilegeController {

    @Autowired
    private IPrivilegeService privilegeService;



    @PostMapping("privilege")
    public PrivilegeModelResponse createPrivilege(@RequestParam("privilege-name") String privilegeName) throws InterruptedException, ExecutionException {
        return privilegeService.createPrivilegeIfNotFound(privilegeName);

    }

    @GetMapping("privilege")
    public List<PrivilegeModelResponse> getPrivileges() throws InterruptedException, ExecutionException {

        return privilegeService.getAllPrivileges();
    }
    @DeleteMapping("privilege/delete/{id}")
    public void deletePrivilege(@PathVariable String id) {
    	privilegeService.deletePrivilege(id);
    }
    
    @PutMapping("privilege/{id}")
    public PrivilegeModelResponse updatePrivilege(@PathVariable String id,@RequestBody UpdatePriviligeRequest privilegeModel) throws InterruptedException, ExecutionException {
    	
	 return privilegeService.updatePrivilege(id, privilegeModel);

    }

}