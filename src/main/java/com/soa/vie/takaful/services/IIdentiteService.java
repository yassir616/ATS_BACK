package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Identite;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateIdentitie;
import com.soa.vie.takaful.responsemodels.IdentiteResponseModel;

import org.springframework.data.domain.Page;

public interface IIdentiteService {
    Page<Identite> getAllIdentites(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException;

    IdentiteResponseModel createIdentite(CreateAndUpdateIdentitie identite)
            throws InterruptedException, ExecutionException;
}
