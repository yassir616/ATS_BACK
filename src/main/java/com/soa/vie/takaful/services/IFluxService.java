package com.soa.vie.takaful.services;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateFlux;
import com.soa.vie.takaful.requestmodels.SearchFlux;
import com.soa.vie.takaful.responsemodels.FluxResponseModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFluxService {

    List<FluxResponseModel> searchFichiersRecus(SearchFlux searchFlux) throws InterruptedException, ExecutionException;

    FluxResponseModel addFlux(CreateAndUpdateFlux createAndUpdateFlux) throws InterruptedException, ExecutionException;
}
