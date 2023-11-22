package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Profession;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateProfession;
import com.soa.vie.takaful.responsemodels.ProfessionResponseModel;

import org.springframework.data.domain.Page;

public interface IProfessionService {
    Page<Profession> getAllProfessions(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException;

    ProfessionResponseModel createProfession(CreateAndUpdateProfession profession)
            throws InterruptedException, ExecutionException;
}
