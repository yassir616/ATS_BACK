package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateAgenda;
import com.soa.vie.takaful.requestmodels.UpdateAgenda;
import com.soa.vie.takaful.responsemodels.AgendaModelResponse;
import com.soa.vie.takaful.services.IAgendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class AgendaController {

    @Autowired
    private IAgendaService agendaService;

    @PostMapping("agenda/add")
	public AgendaModelResponse createEvent(@RequestBody CreateAndUpdateAgenda model) throws InterruptedException, ExecutionException {
        return agendaService.createEvent(model);
    }
    
    @PutMapping("agenda/{id}")
	public List <AgendaModelResponse> updateEvent(@PathVariable String id,@RequestBody UpdateAgenda model) throws InterruptedException, ExecutionException {
        return agendaService.eventUpdate(id, model);
    }

    
    @GetMapping("agenda/{userId}")
	public List<AgendaModelResponse> getEventByUserId(@PathVariable String userId) throws InterruptedException, ExecutionException {
        return agendaService.getEventByIdUser(userId);
  }

  
  @DeleteMapping("agenda/{id}/{userId}")
  public List<AgendaModelResponse> deleteEventById(@PathVariable String id, @PathVariable String userId) throws InterruptedException, ExecutionException {
     return agendaService.deleteEventById(id, userId);
  }
	
}