package com.soa.vie.takaful.services.Impl;

import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.TypeAvenant;
import com.soa.vie.takaful.repositories.ITypeAvenantRepository;
import com.soa.vie.takaful.requestmodels.CreateTypeAvenant;
import com.soa.vie.takaful.responsemodels.TypeAvenantResponseModel;
import com.soa.vie.takaful.services.ITypeAvenantService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TypeAvenantServiceImpl implements ITypeAvenantService {

    @Autowired
    private ITypeAvenantRepository typeAvenantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public TypeAvenantResponseModel createTypeAvenant(CreateTypeAvenant model)throws InterruptedException, ExecutionException {
        log.info("creation type auxiliaire...");

        Thread.sleep(1000L);
        TypeAvenant addtypeAvenant = new TypeAvenant();

        BeanUtils.copyProperties(model, addtypeAvenant);
        addtypeAvenant = this.typeAvenantRepository.save(addtypeAvenant);

        return modelMapper.map(addtypeAvenant, TypeAvenantResponseModel.class);
    }

    @Override
    @Async("asyncExecutor")
    public Page<TypeAvenantResponseModel> getTypeAvenants(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {
        log.info("lister les types auxiliaires");

        Thread.sleep(1000L);
        return typeAvenantRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, TypeAvenantResponseModel.class));

    }

    @Override
    @Async("asyncExecutor")
    public String getCodeById(String id) throws InterruptedException, ExecutionException{
        log.info("Get code by id...");

        Thread.sleep(1000L);   
        return typeAvenantRepository.codeTypeAvenant(id);
    }

}