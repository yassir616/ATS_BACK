package com.soa.vie.takaful.services.Impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.OptionAssurance;
import com.soa.vie.takaful.repositories.IOptionAssuranceRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateOptionAssurance;
import com.soa.vie.takaful.responsemodels.OptionAssuranceModelResponse;
import com.soa.vie.takaful.services.IOptionAssuranceService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class OptionAssuranceServiceImpl implements IOptionAssuranceService{

	@Autowired
	private IOptionAssuranceRepository optionAssuranceRepository;

	@Autowired
	private ModelMapper modelMapper;
	    
	    
	@Override
	@Async("asyncExecutor")
	public OptionAssuranceModelResponse createOptionAssurance(CreateAndUpdateOptionAssurance optionAssurance) throws InterruptedException, ExecutionException{
	log.info("creation d'option assurance ...");
		Thread.sleep(1000L);
    OptionAssurance optionassurance = new OptionAssurance();
   
      
            BeanUtils.copyProperties(optionAssurance, optionassurance);
            return modelMapper.map( optionAssuranceRepository.save(optionassurance), OptionAssuranceModelResponse.class);
	}

	@Override
	@Async("asyncExecutor")
	public OptionAssuranceModelResponse updateOptionAssurance(String optionId, CreateAndUpdateOptionAssurance optionAssurance) throws InterruptedException, ExecutionException{
		log.info("modifier l'option Assurance avec Id : {}", optionId);
        Thread.sleep(1000L);
        Optional<OptionAssurance> assuranceOpt = optionAssuranceRepository.findById(optionId);
        if (assuranceOpt.isPresent()) {
			OptionAssurance optionAssur = assuranceOpt.get();
			BeanUtils.copyProperties(optionAssurance, optionAssur);
			return modelMapper.map( optionAssuranceRepository.save(optionAssur),OptionAssuranceModelResponse.class);
		} else {
        	throw new NoSuchElementException();
		}
	}

	@Override
	@Async("asyncExecutor")
	public Page<OptionAssuranceModelResponse> getOptionAssuarnces(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException{
		log.info("Afficher les options d'assurance");
		Thread.sleep(1000L);
		return optionAssuranceRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, OptionAssuranceModelResponse.class));
	}

	@Override
	@Async("asyncExecutor")
	public OptionAssuranceModelResponse getOptionAssuranceById(String id) throws InterruptedException, ExecutionException{
		Thread.sleep(1000L);
		Optional<OptionAssurance> optionAssurancet = optionAssuranceRepository.findById(id);
		if (optionAssurancet.isPresent()){
			return modelMapper.map( optionAssurancet.get(),OptionAssuranceModelResponse.class);
		}

        else {
            log.info("ce ID n'existe pas : " + id);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"il y'a aucune option d'assurance avec ce id");
        }
	}
	
	 
	
}