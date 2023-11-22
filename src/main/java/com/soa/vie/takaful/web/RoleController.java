package com.soa.vie.takaful.web;

import com.soa.vie.takaful.entitymodels.Role;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateRoleRequestModel;
import com.soa.vie.takaful.responsemodels.RoleModelResponse;
import com.soa.vie.takaful.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("role")
    public Role createRole(@RequestBody CreateAndUpdateRoleRequestModel roleModel) throws Exception {
 
        return roleService.createRoleIfNotExist(roleModel.getName(), roleModel.getPrivileges());
    }

    @PutMapping("role/{id}")
    public RoleModelResponse updateRole(@RequestBody CreateAndUpdateRoleRequestModel roleModel, @PathVariable String id) throws Exception {
     
        return roleService.updateRole(id,roleModel.getName(), roleModel.getPrivileges());
    }

    @GetMapping("role")
    public Page<RoleModelResponse> getRoles(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int size,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws Exception {

        return  roleService.getAllRoles(page, size, sort,direction);
    }
}