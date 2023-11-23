package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Periodicite;
import com.soa.vie.takaful.repositories.IPeriodiciteRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePeriodicite;
import com.soa.vie.takaful.responsemodels.PeriodiciteResponseModel;
import com.soa.vie.takaful.services.IPeriodiciteService;
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
public class PeriodiciteServiceImpl implements IPeriodiciteService {

	@Autowired
	private IPeriodiciteRepository periodiciteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public PeriodiciteResponseModel createPeriodicite(CreateAndUpdatePeriodicite model)
			throws InterruptedException, ExecutionException {
		log.info("creation de periodicité  ...");
		Thread.sleep(1000L);
		Periodicite periodicite = new Periodicite();

		BeanUtils.copyProperties(model, periodicite);
		periodicite = periodiciteRepository.save(periodicite);

		return modelMapper.map(periodicite, PeriodiciteResponseModel.class);
	}

	@Override
	@Async("asyncExecutor")
	public PeriodiciteResponseModel updatePeriodicite(String id, CreateAndUpdatePeriodicite model)
			throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		Optional<Periodicite> periodiciteOptional = periodiciteRepository.findById(id);
		if (!periodiciteOptional.isPresent()) {
			log.info("y'a aucune periodicité avec ce id: " + id);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ce ID n'existe pas");
		}
		Periodicite periodiciteEntity = periodiciteOptional.get();
		periodiciteEntity.setAbb(model.getAbb());
		periodiciteEntity.setLibelle(model.getLibelle());

		return modelMapper.map(periodiciteRepository.save(periodiciteEntity), PeriodiciteResponseModel.class);
	}

	@Override
	@Async("asyncExecutor")
	public Page<PeriodiciteResponseModel> getPeriodicites(int page, int limit, String sort, String direction)
			throws InterruptedException, ExecutionException {
		log.info("lister les periodicites");
		Thread.sleep(1000L);
		return periodiciteRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, PeriodiciteResponseModel.class));
	}

	@Override
	@Async("asyncExecutor")
	public PeriodiciteResponseModel getPeriodiciteByAbb(String abb)
			throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		Optional<Periodicite> periodiciteEntity = periodiciteRepository.findByAbb(abb);
		if (periodiciteEntity.isPresent()) {
			return modelMapper.map(periodiciteEntity.get(), PeriodiciteResponseModel.class);
		} else {

			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun service avec ce code");
		}
	}

}
