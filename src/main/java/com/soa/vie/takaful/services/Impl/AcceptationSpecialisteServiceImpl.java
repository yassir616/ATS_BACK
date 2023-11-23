package com.soa.vie.takaful.services.Impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationSpecialiste;
import com.soa.vie.takaful.repositories.IAcceptationRepository;
import com.soa.vie.takaful.repositories.IAcceptationSpecialisteRepository;
import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;

import com.soa.vie.takaful.responsemodels.AcceptationSpecialisteModelResponse;
import com.soa.vie.takaful.services.IAcceptationSpecialisteService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AcceptationSpecialisteServiceImpl implements IAcceptationSpecialisteService {

    @Autowired
    private IAcceptationSpecialisteRepository acceptationSpecialisteRepository;

    @Autowired
    private IAcceptationRepository acceptationRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Async("asyncExecutor")
    public AcceptationSpecialisteModelResponse createAcceptationSpecialiste(CreateallAcceptationTestKindModelRequest model)throws InterruptedException, ExecutionException {
       
        Thread.sleep(1000L);
        AcceptationSpecialiste acceptationSpecialiste = new AcceptationSpecialiste();
        BeanUtils.copyProperties(model, acceptationSpecialiste);
        return modelMapper.map(acceptationSpecialisteRepository.save(acceptationSpecialiste), AcceptationSpecialisteModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public List<AcceptationSpecialisteModelResponse> acceptationsSpecialisteByAcceptation(String acceptationId) throws InterruptedException, ExecutionException{
       
        Thread.sleep(1000L);
        Optional<Acceptation> acceptationOptional = acceptationRepository.findById(acceptationId);
        if(!acceptationOptional.isPresent()){
            throw new NoSuchElementException("ListAcceptationSpecialiste : acceptation n'existe pas ");
        }
        else{
            Acceptation acceptation= acceptationOptional.get();
            return acceptationSpecialisteRepository.findByAcceptation(acceptation).stream()
                    .map(o -> modelMapper.map(o,AcceptationSpecialisteModelResponse.class)).collect(Collectors.toList());
        }
    }

    @Override
    @Async("asyncExecutor")
    public AcceptationSpecialisteModelResponse updateAcceptationSpecialiste(CreateallAcceptationTestKindModelRequest model, String id)throws InterruptedException, ExecutionException {
        
        Thread.sleep(1000L);
        Optional<AcceptationSpecialiste> acceptationSpecialiste = acceptationSpecialisteRepository.findById(id);


        if(acceptationSpecialiste.isPresent()){
            AcceptationSpecialiste acceptationSpecialiste2 = acceptationSpecialiste.get();
            BeanUtils.copyProperties(model,acceptationSpecialiste2 );
            return modelMapper.map(acceptationSpecialisteRepository.save(acceptationSpecialiste2),AcceptationSpecialisteModelResponse.class);
            }

        throw new NoSuchElementException("wrong data");
      }
    }
    