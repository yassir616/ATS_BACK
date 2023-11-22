package com.soa.vie.takaful.servicesImplementation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Agenda;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.repositories.IAgendaRepository;
import com.soa.vie.takaful.repositories.ITakafulUserRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateAgenda;
import com.soa.vie.takaful.requestmodels.UpdateAgenda;

import com.soa.vie.takaful.responsemodels.AgendaModelResponse;
import com.soa.vie.takaful.services.IAgendaService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AgendaServiceImpl implements IAgendaService {
    @Autowired
    private IAgendaRepository agendaRepository;
    
    @Autowired
    private ITakafulUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Async("asyncExecutor")
    public AgendaModelResponse createEvent(CreateAndUpdateAgenda model)throws InterruptedException, ExecutionException {
        log.info("creation Event ...");
        Thread.sleep(1000L);
        Agenda event = new Agenda();


            Optional<TakafulUser> user = userRepository.findById(model.getUserId());

            if(user.isPresent()){
                event.setUser(user.get());
                BeanUtils.copyProperties(model, event);
                return modelMapper.map(agendaRepository.save(event),AgendaModelResponse.class);
            
        } else {
            log.error("la creation d'événement est bien faite");
            throw new NoSuchElementException("la creation d'événement est fqite avec succés");
        }
    }

    @Override
    @Async("asyncExecutor")
    public List<AgendaModelResponse> eventUpdate(String id, UpdateAgenda model)throws InterruptedException, ExecutionException {
        log.info("modifier l'événement avec Id : {}", id);
        Thread.sleep(1000L);
            Optional<Agenda> eventOpt = agendaRepository.findById(id);
            Optional<TakafulUser> user = userRepository.findById(model.getUserId());

            if (eventOpt.isPresent() && user.isPresent()) {
            	Agenda agenda = eventOpt.get();
                agenda.setUser(user.get());
                BeanUtils.copyProperties(model, agenda);
                 agendaRepository.save(agenda);
                 return agendaRepository.findByUserId(model.getUserId()).stream()
                         .map(o -> modelMapper.map(o,AgendaModelResponse.class))
                         .collect(Collectors.toList());

            } else {
                log.error("la modification d'événement avec ce Id ({}): est erronée",id);
                throw new NoSuchElementException("la modification d'événement avec ce Id est erronée");
            }
    }

    @Override
    @Async("asyncExecutor")
    public List<AgendaModelResponse> getEventByIdUser(String id) throws InterruptedException, ExecutionException{

        Thread.sleep(1000L);
        log.info("lister les contrats décès acceptée");
        return agendaRepository.findByUserId(id).stream()
                .map(o -> modelMapper.map(o,AgendaModelResponse.class))
                .collect(Collectors.toList());
    }

   
    @Override
    @Async("asyncExecutor")
    public List<AgendaModelResponse> deleteEventById(String id, String userId)throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        agendaRepository.deleteById(id);
        return agendaRepository.findByUserId(userId).stream()
                .map(o -> modelMapper.map(o,AgendaModelResponse.class))
                .collect(Collectors.toList());

    }

 
  
}