package com.soa.vie.takaful.services.Impl;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.repositories.IVerdictRepository;

import com.soa.vie.takaful.responsemodels.VerdictModelResponse;
import com.soa.vie.takaful.services.IVerdictService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VerdictServiceImpl implements IVerdictService {

    @Autowired
    private IVerdictRepository verdictRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public List<VerdictModelResponse> getVerdicts(String type)throws InterruptedException, ExecutionException {
        
        Thread.sleep(1000L);

        return  verdictRepository.findByType(type)
                .stream()
                .map(o->modelMapper.map(o,VerdictModelResponse.class))
                .collect(Collectors.toList());
    }
    
}