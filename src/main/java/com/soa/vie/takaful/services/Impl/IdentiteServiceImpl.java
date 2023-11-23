package com.soa.vie.takaful.services.Impl;


import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Identite;
import com.soa.vie.takaful.repositories.IIdentiteRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateIdentitie;
import com.soa.vie.takaful.responsemodels.IdentiteResponseModel;
import com.soa.vie.takaful.services.IIdentiteService;
import com.soa.vie.takaful.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class IdentiteServiceImpl  implements IIdentiteService {

    @Autowired
    private IIdentiteRepository identiteRepos;

    @Override
    @Async("asyncExecutor")
    public Page<Identite> getAllIdentites(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        return identiteRepos.findAll(Pagination.pageableRequest(page, limit, sort, direction));
    }

    @Override
    public IdentiteResponseModel createIdentite(CreateAndUpdateIdentitie identite)
            throws InterruptedException, ExecutionException {
        // TODO Auto-generated method stub
        return null;
    }
 

}
