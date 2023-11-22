package com.soa.vie.takaful.servicesImplementation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Auxiliaire;
import com.soa.vie.takaful.entitymodels.TypeAuxiliaire;
import com.soa.vie.takaful.repositories.IAuxiliaireRepository;
import com.soa.vie.takaful.repositories.ITypeAuxiliaireRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateAuxiliaire;
import com.soa.vie.takaful.responsemodels.AuxiliaireModelResponse;
import com.soa.vie.takaful.services.IAuxiliaireService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class AuxiliaireServiceImpl implements IAuxiliaireService {

	@Autowired
	private IAuxiliaireRepository auxiliaireRepository;

	@Autowired
	private ITypeAuxiliaireRepository typeAuxiliairerepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public AuxiliaireModelResponse createAuxiliaire(CreateAndUpdateAuxiliaire model)throws InterruptedException, ExecutionException {
		log.info("creation auxiliaire ...");

		Thread.sleep(1000L);
		Auxiliaire auxiliaire = new Auxiliaire();

		Optional<TypeAuxiliaire> type = typeAuxiliairerepository.findById(model.getTypeAuxiliaireId());

		if (type.isPresent()) {
			auxiliaire.setTypeAuxiliaire(type.get());
			BeanUtils.copyProperties(model, auxiliaire);
			return modelMapper.map(auxiliaireRepository.save(auxiliaire),AuxiliaireModelResponse.class);

		} else {
			log.error("la creation d'auxiliaire est erronée");
			throw new NoSuchElementException("la creation d'auxiliaire est erronée");
		}
	}

	@Override
	@Async("asyncExecutor")
	public AuxiliaireModelResponse updateAuxiliaire(String id, CreateAndUpdateAuxiliaire model)throws InterruptedException, ExecutionException {
		log.info("modifier l'auxiliaire avec Id : {}", id);

		Thread.sleep(1000L);
		Optional<Auxiliaire> auxiliaireOpt = auxiliaireRepository.findById(id);
		Optional<TypeAuxiliaire> typeAuxiliaire = typeAuxiliairerepository.findById(model.getTypeAuxiliaireId());

		if (auxiliaireOpt.isPresent() && typeAuxiliaire.isPresent()) {
			Auxiliaire auxiliaire = auxiliaireOpt.get();
			auxiliaire.setTypeAuxiliaire(typeAuxiliaire.get());
			BeanUtils.copyProperties(model, auxiliaire);
			return modelMapper.map(auxiliaireRepository.save(auxiliaire),AuxiliaireModelResponse.class);

		} else {
			log.error("la modification d'auxiliaire avec ce Id ({}): est erronée", id);
			throw new NoSuchElementException("la modification d'auxiliaire avec ce Id est erronée");
		}

	}

	@Override
	@Async("asyncExecutor")
	public Page<AuxiliaireModelResponse> getAuxiliaires(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {
		log.info("lister les auxiliaires");
		Thread.sleep(1000L);
		return auxiliaireRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o,AuxiliaireModelResponse.class));

	}

	@Override
	@Async("asyncExecutor")
	public AuxiliaireModelResponse getAuxiliaireById(String id)throws InterruptedException, ExecutionException {
		Thread.sleep(1000L);
		Optional<Auxiliaire>auxiliaireOptional = auxiliaireRepository.findById(id);
		if (!auxiliaireOptional.isPresent()) {
			log.info("ce ID n'existe pas : " + id);
			throw new NoSuchElementException("il y'a aucune auxiliaire avec ce id");
		} else {
			return modelMapper.map(auxiliaireOptional.get(),AuxiliaireModelResponse.class);

		}

	}

	@Override
	@Async("asyncExecutor")
	public List<AuxiliaireModelResponse> getAuxiliairebytype(String typeAuxiliaire)throws InterruptedException, ExecutionException {

		Thread.sleep(1000L);
		Optional<TypeAuxiliaire> type = typeAuxiliairerepository.findByLibelle(typeAuxiliaire);
		if (type.isPresent()) {
			return this.auxiliaireRepository.findByTypeAuxiliaire(type.get()).stream()
					.map(o -> modelMapper.map(o,AuxiliaireModelResponse.class))
					.collect(Collectors.toList());
		} else {
			log.error("pas d'auxiliaire avec ce type d'auxiliaire");
			throw new NoSuchElementException("pas d'auxiliaire avec ce type d'auxiliaire");
		}
	}

	@Override
	@Async("asyncExecutor")
	public List<Auxiliaire> getAuxiliairebystatut()throws InterruptedException, ExecutionException {
		return auxiliaireRepository.findAuxiliaireByStatus();
	}
	
}


