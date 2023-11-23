package com.soa.vie.takaful.services.Impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationLaboratoire;
import com.soa.vie.takaful.repositories.IAcceptationLaboratoireRepository;
import com.soa.vie.takaful.repositories.IAcceptationRepository;
import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;

import com.soa.vie.takaful.responsemodels.AcceptationLaboratoireModelResponse;
import com.soa.vie.takaful.services.IAcceptationLaboratoireService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AcceptationLaboratoireServiceImpl implements IAcceptationLaboratoireService {

    @Autowired
    private IAcceptationLaboratoireRepository acceptationLaboratoireRepository;

    @Autowired
    private IAcceptationRepository acceptationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public AcceptationLaboratoireModelResponse createAcceptationLaboratoire(CreateallAcceptationTestKindModelRequest model)throws InterruptedException, ExecutionException {
        // Thread.sleep(1000L);
        AcceptationLaboratoire acceptationLaboratoire = new AcceptationLaboratoire();
        BeanUtils.copyProperties(model, acceptationLaboratoire);
        return modelMapper.map(acceptationLaboratoireRepository.save(acceptationLaboratoire),AcceptationLaboratoireModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public List<AcceptationLaboratoireModelResponse> acceptationsLaboratoireByAcceptation(String acceptationId) throws InterruptedException, ExecutionException{
       // Thread.sleep(1000L);
        Optional<Acceptation> acceptationOptional = acceptationRepository.findById(acceptationId);
        if(!acceptationOptional.isPresent()){
            throw new NoSuchElementException("ListAcceptationLaboratoire : acceptation n'existe pas ");
        }
        else{
            Acceptation acceptation= acceptationOptional.get();
            return acceptationLaboratoireRepository.findByAcceptation(acceptation)
                    .stream()
                    .map(o -> modelMapper.map(o,AcceptationLaboratoireModelResponse.class))
                    .collect(Collectors.toList());
        }
    }

}