package com.soa.vie.takaful.services.Impl;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.repositories.IFamilleProduit;
import com.soa.vie.takaful.responsemodels.FamilleProduitResponseModel;
import com.soa.vie.takaful.services.IFamilleProduitService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class FamilleProduitServiceImpl implements IFamilleProduitService {

    @Autowired
    private IFamilleProduit FamilleProduitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public String getIdFamilleProduct(String name)throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        return FamilleProduitRepository.idFamilleProduit(name);
    }

    @Override
    @Async("asyncExecutor")
    public Page<FamilleProduitResponseModel> getFamilleProduits(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        return FamilleProduitRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, FamilleProduitResponseModel.class));
    }

  

}
