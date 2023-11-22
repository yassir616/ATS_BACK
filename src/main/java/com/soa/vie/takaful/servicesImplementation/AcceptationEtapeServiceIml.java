package com.soa.vie.takaful.servicesImplementation;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.AcceptationEtape;
import com.soa.vie.takaful.repositories.IAcceptationEtapeRepository;
import com.soa.vie.takaful.requestmodels.CreateAcceptationEtapeModelRequest;

import com.soa.vie.takaful.responsemodels.AcceptationEtapeModelResponse;
import com.soa.vie.takaful.services.IAcceptationEtapeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AcceptationEtapeServiceIml implements IAcceptationEtapeService {


    @Autowired
    private IAcceptationEtapeRepository acceptationEtapeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public AcceptationEtapeModelResponse createAcceptationEtape(CreateAcceptationEtapeModelRequest model)throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        AcceptationEtape acceptationEtape = new AcceptationEtape();
        BeanUtils.copyProperties(model, acceptationEtape);
        return modelMapper.map(acceptationEtapeRepository.save(acceptationEtape), AcceptationEtapeModelResponse.class);
    }
    

}