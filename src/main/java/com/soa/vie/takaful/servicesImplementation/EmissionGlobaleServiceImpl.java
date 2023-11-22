package com.soa.vie.takaful.servicesImplementation;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.soa.vie.takaful.entitymodels.EmissionGlobale;
import com.soa.vie.takaful.repositories.IEmissionGlobaleRepository;
import com.soa.vie.takaful.services.IEmissionGlobale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmissionGlobaleServiceImpl implements IEmissionGlobale{
	
	@Autowired
	public IEmissionGlobaleRepository emissionGlobaleRepository; 

	@Override
	public Page<EmissionGlobale> getAllEmissionGlobale(int pageNumber,int pageSize,String sortCol,Boolean direction) throws InterruptedException, ExecutionException {
		log.info("getting emissions gloables...");
		Pageable page=PageRequest.of(pageNumber, pageSize,Sort.by(direction ? Direction.ASC :  Direction.DESC , sortCol));
		return emissionGlobaleRepository.findAllEmissions(page);
	}

	@Override
	public EmissionGlobale findByNumeroLot(String numeroLot) throws InterruptedException, ExecutionException {
		
		return emissionGlobaleRepository.findByNumeroLot(numeroLot);
	}

}
