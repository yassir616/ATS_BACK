package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateAgenda;
import com.soa.vie.takaful.requestmodels.UpdateAgenda;
import com.soa.vie.takaful.responsemodels.AgendaModelResponse;


public interface IAgendaService {

    public AgendaModelResponse createEvent (CreateAndUpdateAgenda model) throws InterruptedException, ExecutionException;

    public List <AgendaModelResponse> eventUpdate(String id, UpdateAgenda model) throws InterruptedException, ExecutionException;
   
    public List <AgendaModelResponse> getEventByIdUser(String id) throws InterruptedException, ExecutionException;
    
    public List <AgendaModelResponse> deleteEventById(String id, String userId) throws InterruptedException, ExecutionException;


}