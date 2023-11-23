package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.SousCategorie;
import com.soa.vie.takaful.repositories.ISousCategorieRepository;
import com.soa.vie.takaful.requestmodels.CreateUpdateCatAndSubCatRequestModel;
import com.soa.vie.takaful.responsemodels.SousCategorieModelResponse;
import com.soa.vie.takaful.services.ISousCategorieService;
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
public class SousCategorieServiceImpl implements ISousCategorieService {

    @Autowired
    private ISousCategorieRepository sousCategorieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public SousCategorieModelResponse createSousCategorie(CreateUpdateCatAndSubCatRequestModel requestModel)throws InterruptedException, ExecutionException {

        log.info("creating Sous Categorie ...");

        Thread.sleep(1000L);
        SousCategorie categorie = new SousCategorie();

        if (!"".equals(requestModel.getCode())) {

            BeanUtils.copyProperties(requestModel, categorie);
            return modelMapper.map(sousCategorieRepository.save(categorie), SousCategorieModelResponse.class);

        } else {
            log.error("Error creating sub categorie you need to insert all required values");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Error sub creating categorie you need to insert all required values");
        }
    }

    @Override
    @Async("asyncExecutor")
    public SousCategorieModelResponse updateSousCategorie(String id,
            CreateUpdateCatAndSubCatRequestModel requestModel)throws InterruptedException, ExecutionException {

        log.info("updating Categorie with Id : {}", id);

        Thread.sleep(1000L);
        if (!"".equals(requestModel.getCode())) {

            Optional<SousCategorie> catOpt = sousCategorieRepository.findById(id);

            if (catOpt.isPresent()) {
                SousCategorie sousCategorie = catOpt.get();
                BeanUtils.copyProperties(requestModel, sousCategorie);
                return modelMapper.map(sousCategorieRepository.save(sousCategorie), SousCategorieModelResponse.class);

            } else {
                log.error("Error updating sous categorie with Id ({}): please check the given categorie", id);
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                        "Error updating sous categorie, please check the given categorie");
            }
        } else {
            log.error("Error updating sous categorie with Id ({}): please insert required values", id);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Error updating sous categorie, please insert required values");
        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<SousCategorieModelResponse> getSousCategories(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException{
        log.info("Getting sub categories");

        Thread.sleep(1000L);
        return sousCategorieRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, SousCategorieModelResponse.class));
    }
}