package com.soa.vie.takaful.services.Impl;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Privilege;
import com.soa.vie.takaful.repositories.IPrivilegeRepository;
import com.soa.vie.takaful.requestmodels.UpdatePriviligeRequest;

import com.soa.vie.takaful.responsemodels.PrivilegeModelResponse;
import com.soa.vie.takaful.services.IPrivilegeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;


@Service
@Transactional
@Slf4j
class PrivilegeServiceImpl implements IPrivilegeService {

  @Autowired
  private IPrivilegeRepository privilegeRepository;

  @Autowired
  private ModelMapper modelMapper;
  

  @Override
  @Async("asyncExecutor")
  public PrivilegeModelResponse createPrivilegeIfNotFound(String privilegeName)throws InterruptedException,ExecutionException {
    Thread.sleep(1000L);
    // searching for the privilege in database.
    Optional<Privilege> privilegeOpt = privilegeRepository.findPrivilegeByName(privilegeName);

    // creating a new privilege in case it doesn't exist before.
    if (!privilegeOpt.isPresent()) {
      log.info("Privilege with name {} does not alredy exist, creating a new one ...", privilegeName);

      Privilege privilege = new Privilege();
      privilege.setName(privilegeName);

      return modelMapper.map(privilegeRepository.save(privilege),PrivilegeModelResponse.class);

    } else {

      log.info("Privilege with name {} found", privilegeName);

    }

    return modelMapper.map(privilegeOpt.get(),PrivilegeModelResponse.class);
  }

  @Override
  @Async("asyncExecutor")
  public List<PrivilegeModelResponse> getAllPrivileges() throws InterruptedException,ExecutionException{
    Thread.sleep(1000L);
      return ((List<Privilege>) privilegeRepository.findAll()).stream()
              .map(o -> modelMapper.map(o,PrivilegeModelResponse.class))
              .collect(Collectors.toList());
  }
  @Override
  public void deletePrivilege(String id) {
	  privilegeRepository.deleteById(id);
  }

@Override
@Async("asyncExecutor")
public PrivilegeModelResponse updatePrivilege(String privilegeId, UpdatePriviligeRequest privilegeModel)throws InterruptedException,ExecutionException {
	Thread.sleep(1000L);
	Optional<Privilege> privilegeOptional = privilegeRepository.findById(privilegeId);
    if (!privilegeOptional.isPresent()) {
        log.info("y'a aucune exclusion avec ce id: " + privilegeId);
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"ce ID n'existe pas");
    }
    Privilege privilegeEntity = privilegeOptional.get();
    privilegeEntity.setName(privilegeModel.getName());

    return modelMapper.map(privilegeRepository.save(privilegeEntity),PrivilegeModelResponse.class);
}

}