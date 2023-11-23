package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;
import javax.websocket.EncodeException;

import com.soa.vie.takaful.entitymodels.TypeAuxiliaire;
import com.soa.vie.takaful.repositories.ITypeAuxiliaireRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypeAuxiliaire;
import com.soa.vie.takaful.responsemodels.TypeAuxiliaireModelResponse;
import com.soa.vie.takaful.services.ITypeAuxiliaireService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TypeAuxiliaireServiceImpl implements ITypeAuxiliaireService {

    @Autowired
    private ITypeAuxiliaireRepository typeAuxiliaireRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public TypeAuxiliaireModelResponse createTypeAuxiliaire(CreateAndUpdateTypeAuxiliaire model) throws InterruptedException, ExecutionException{
        log.info("creation type auxiliaire...");
        Thread.sleep(1000L);
        TypeAuxiliaire addtypeauxiliaire = new TypeAuxiliaire();
        String code = model.getCode();

        Optional<TypeAuxiliaire> typeauxiliaireexists = typeAuxiliaireRepository.findByCode(code);

        if (typeauxiliaireexists.isPresent()) {
            log.info("ce type auxiliaire existe déja");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ce type auxiliaire existe déja");
        }

        else {

            BeanUtils.copyProperties(model, addtypeauxiliaire);
            addtypeauxiliaire = this.typeAuxiliaireRepository.save(addtypeauxiliaire);

        }

        return modelMapper.map(addtypeauxiliaire, TypeAuxiliaireModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public TypeAuxiliaireModelResponse updateTypeAuxiliaire(String id, CreateAndUpdateTypeAuxiliaire model)throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        Optional<TypeAuxiliaire> typeAuxiliaireOptional = typeAuxiliaireRepository.findById(id);
        if (!typeAuxiliaireOptional.isPresent()) {
            log.info("aucun type d'auxiliaire avec ce Id : " + id);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ce ID n'existe pas");
        }
        TypeAuxiliaire typeAuxiliaireEntity = typeAuxiliaireOptional.get();
        typeAuxiliaireEntity.setCode(model.getCode());
        typeAuxiliaireEntity.setLibelle(model.getLibelle());
        return modelMapper.map(typeAuxiliaireRepository.save(typeAuxiliaireEntity), TypeAuxiliaireModelResponse.class);
    }

    @Override
    @Async("asyncExecutor")
    public Page<TypeAuxiliaireModelResponse> getTypeAuxiliaires(int page, int limit, String sort, String direction) throws InterruptedException, EncodeException{
        log.info("lister les types auxiliaires");
        Thread.sleep(1000L);
        return typeAuxiliaireRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, TypeAuxiliaireModelResponse.class));
    }

}
