package com.soa.vie.takaful.servicesImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.TypePersonneMorale;
import com.soa.vie.takaful.repositories.ITypePersonneMoraleRepository;

import com.soa.vie.takaful.responsemodels.TypePersonneMoraleModelResponse;
import com.soa.vie.takaful.services.ITypePersonneMoraleService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TypePersonneMoraleServiceImpl implements ITypePersonneMoraleService {

    @Autowired
    private ITypePersonneMoraleRepository typePersonneMoraleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public List<TypePersonneMoraleModelResponse> getTypesPersonneMorales()
            throws InterruptedException, ExecutionException {
        log.info("Getting types personne morale");

        Thread.sleep(1000L);
        List<TypePersonneMoraleModelResponse> typePersonneMoraleModelResponses = new ArrayList<>();
        for (TypePersonneMorale typePersonneMorale : typePersonneMoraleRepository.findAll()) {
            typePersonneMoraleModelResponses
                    .add(modelMapper.map(typePersonneMorale, TypePersonneMoraleModelResponse.class));
        }
        return typePersonneMoraleModelResponses;
    }

    @Override
    @Async("asyncExecutor")
    public TypePersonneMoraleModelResponse getTypePersonneMoraleByLibelle(String libelle)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        java.util.Optional<TypePersonneMorale> typePersonneMorale = typePersonneMoraleRepository
                .findByLibelle(libelle);
        if (typePersonneMorale.isPresent()) {
            return modelMapper.map(typePersonneMorale.get(), TypePersonneMoraleModelResponse.class);
        } else {

            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun service avec cette libelle");
        }
    }
}