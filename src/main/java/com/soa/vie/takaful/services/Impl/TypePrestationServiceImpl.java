package com.soa.vie.takaful.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.entitymodels.FamilleProduit;
import com.soa.vie.takaful.entitymodels.TypePrestation;
import com.soa.vie.takaful.repositories.IFamilleProduit;
import com.soa.vie.takaful.repositories.ITypePrestationRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePrestationRequestModel;
import com.soa.vie.takaful.responsemodels.TypePrestationModelResponse;
import com.soa.vie.takaful.services.ITypePrestationService;
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
@Slf4j
public class TypePrestationServiceImpl implements ITypePrestationService {

    @Autowired
    private ITypePrestationRepository typePrestationRepository;

    @Autowired
    private IFamilleProduit familleProduitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public TypePrestationModelResponse createTypePrestation(CreateAndUpdateTypePrestationRequestModel model)throws InterruptedException, ExecutionException {

        log.info("creation Type Prestation ...");

        Thread.sleep(1000L);
        TypePrestation typePrestation = new TypePrestation();
 
        Optional<FamilleProduit> type = this.familleProduitRepository.findById(model.getFamilleId());
        if (!"".equals(model.getCode()) && !"".equals(model.getLibelle()) && type.isPresent()) {
            typePrestation.setFamille(type.get());
            
            BeanUtils.copyProperties(model, typePrestation);

            TypePrestation mTypePrestation = this.typePrestationRepository.save(typePrestation);
            return modelMapper.map(mTypePrestation,TypePrestationModelResponse.class);
        } else {
            log.error("la creation de Type prestation est erroné ");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"la creation de Type prestation est erroné ");
        }
    }

    @Override
    @Async("asyncExecutor")
    public TypePrestationModelResponse updateTypePrestation(CreateAndUpdateTypePrestationRequestModel model,
                                                            String typePrestationId) throws InterruptedException, ExecutionException{

        log.info("modifier Type Prestation avec ce Id : {}", typePrestationId);

        Thread.sleep(1000L);
        if (!"".equals(model.getCode()) && !"".equals(model.getLibelle())) {

            Optional<TypePrestation> typePrestationOpt = typePrestationRepository.findById(typePrestationId);

            if (typePrestationOpt.isPresent()) {
                TypePrestation typePrestation = typePrestationOpt.get();
                BeanUtils.copyProperties(model, typePrestation);
                return modelMapper.map(typePrestationRepository.save(typePrestation),TypePrestationModelResponse.class);

            } else {
                log.error("la modification de Type prestation avec Id ({}): est erroné",
                        typePrestationId);
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"la modification de Type prestation est erroné : aucun type avec ce id");
            }
        } else {
            log.error("la modification de Type prestation avec Id ({}): est erroné", typePrestationId);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"la modification de Type prestation est erroné");
        }
    }

    @Override
    @Async("asyncExecutor")
    public TypePrestationModelResponse getTypePrestation(String typePrestationId)throws InterruptedException, ExecutionException {

        log.info("afficher type prestation avec Id {}", typePrestationId);

        Thread.sleep(1000L);
        Optional<TypePrestation> typePrestationOpt = typePrestationRepository.findById(typePrestationId);

        if (typePrestationOpt.isPresent()) {
            return modelMapper.map(typePrestationOpt.get(),TypePrestationModelResponse.class);
        } else {
            log.error("aucun type prestation avec l'Id donné : {}", typePrestationId);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"aucun type prestation avec l'Id donné");
        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<TypePrestationModelResponse> getTypesPrestation(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {
        log.info("Getting prestation types");

        Thread.sleep(1000L);
        return typePrestationRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, TypePrestationModelResponse.class));
    }
    
    @Override
    @Async("asyncExecutor")
    public List<TypePrestationModelResponse> getListPrestation(String id)throws InterruptedException, ExecutionException
    {

        Thread.sleep(1000L);
        return typePrestationRepository
        .findByFamilleId(id)
        .stream()
        .map(o -> modelMapper.map(o,TypePrestationModelResponse.class))
        .collect(Collectors.toList());
    	
    }

}