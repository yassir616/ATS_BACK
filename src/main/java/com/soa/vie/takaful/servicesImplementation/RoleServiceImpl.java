package com.soa.vie.takaful.servicesImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Privilege;
import com.soa.vie.takaful.entitymodels.Role;
import com.soa.vie.takaful.repositories.IRoleRepository;
import com.soa.vie.takaful.responsemodels.RoleModelResponse;
import com.soa.vie.takaful.services.IPrivilegeService;
import com.soa.vie.takaful.services.IRoleService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IPrivilegeService privilgeService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    @Async("asyncExecutor")
    public Role createRoleIfNotExist(String roleName, List<Privilege> privileges) throws InterruptedException, ExecutionException {
       
        Thread.sleep(1000L);
      
        // search for role if already exist in database
        log.info("Searching for Role ...");
        Role role = roleRepository.findRoleByName(roleName);

        // create new Role in case it doesn't exist
        if (role == null) {
            log.info("Role does not exist, creating a new one ...");

            role = new Role();
            role.setName(roleName);
            List<Privilege> userPrivileges = new ArrayList<>();
            if (null != privileges) {
                for (Privilege privilege : privileges) {

                    userPrivileges.add(modelMapper.map(privilgeService.createPrivilegeIfNotFound(privilege.getName()),
                            Privilege.class));
                    // userPrivileges.add(privilgeService.createPrivilegeIfNotFound(privilege.getName()));
                }
            }
            role.setPrivileges(userPrivileges);
            roleRepository.save(role);

        } else {

            log.info("Role does alredy exist");

        }
        return role;
    }

    @Transactional
    @Override
    @Async("asyncExecutor")
    public RoleModelResponse updateRole(String roleId, String roleName, List<Privilege> privileges) throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        // search for role if already exist in database
        log.info("Searching for Role with id : {}", roleId);
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        // update the Role if exist
        if (roleOptional.isPresent()) {
            log.info("Updating Role ...");
            Role role = roleOptional.get();

            role.setName(roleName);
            List<Privilege> userPrivileges = new ArrayList<>();
            if (null != privileges) {

                for (Privilege privilege : privileges) {
                    userPrivileges.add(modelMapper.map(privilgeService.createPrivilegeIfNotFound(privilege.getName()),
                            Privilege.class));
                }
            }
            role.setPrivileges(userPrivileges);
            return modelMapper.map(roleRepository.save(role), RoleModelResponse.class);

        } else {

            log.error("No Role found with id : {}", roleId);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "this Role doesn't exist !!");

        }

    }

    @Override
    @Async("asyncExecutor")
    public Page<RoleModelResponse> getAllRoles(int page, int size, String sort, String direction) throws InterruptedException, ExecutionException{

        Thread.sleep(1000L);
        return roleRepository.findAll(Pagination.pageableRequest(page, size, sort, direction))
                .map(o -> modelMapper.map(o, RoleModelResponse.class));
    }
}