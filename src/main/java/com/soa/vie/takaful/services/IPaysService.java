package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Pays;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePays;
import com.soa.vie.takaful.responsemodels.PaysResponseModel;
import org.springframework.data.domain.Page;

public interface IPaysService {
    Page<Pays> getAllPayss(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException;

    PaysResponseModel createPays(CreateAndUpdatePays pays)
            throws InterruptedException, ExecutionException;
}
