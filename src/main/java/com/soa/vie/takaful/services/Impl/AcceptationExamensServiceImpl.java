package com.soa.vie.takaful.services.Impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.entitymodels.AcceptationExamens;
import com.soa.vie.takaful.repositories.IAcceptationExamensRepository;
import com.soa.vie.takaful.repositories.IAcceptationRepository;

import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationExamensModelResponse;
import com.soa.vie.takaful.services.IAcceptationExamensService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AcceptationExamensServiceImpl implements IAcceptationExamensService {

    @Autowired
    private IAcceptationExamensRepository acceptationExamensRepository;

    @Autowired
    private IAcceptationRepository acceptationRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Async("asyncExecutor")
    public AcceptationExamensModelResponse createAcceptationExamens(CreateallAcceptationTestKindModelRequest model)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        AcceptationExamens acceptationExamens = new AcceptationExamens();

      
        BeanUtils.copyProperties(model, acceptationExamens);
        return modelMapper.map(acceptationExamensRepository.save(acceptationExamens),
                AcceptationExamensModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public List<AcceptationExamensModelResponse> acceptationsExamensByAcceptation(String acceptationId)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        Optional<Acceptation> acceptationOptional = acceptationRepository.findById(acceptationId);
        if (!acceptationOptional.isPresent()) {
            throw new NoSuchElementException("ListAcceptationExamens : acceptation n'existe pas ");
        } else {
            Acceptation acceptation = acceptationOptional.get();

            return acceptationExamensRepository.findByAcceptation(acceptation).stream()
                    .map(o -> modelMapper.map(o, AcceptationExamensModelResponse.class)).collect(Collectors.toList());
        }
    }

  
}