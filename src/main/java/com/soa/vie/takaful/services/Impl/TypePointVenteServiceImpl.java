package com.soa.vie.takaful.services.Impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.TypePointVente;
import com.soa.vie.takaful.repositories.ITypePointVenteRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePointVente;
import com.soa.vie.takaful.responsemodels.TypePointVenteModelResponse;
import com.soa.vie.takaful.services.ITypePointVenteService;
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
@Slf4j
public class TypePointVenteServiceImpl implements ITypePointVenteService {
	
	@Autowired
	private ITypePointVenteRepository typePointVenteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public TypePointVenteModelResponse createTypePointVente(CreateAndUpdateTypePointVente model) throws InterruptedException, ExecutionException{

		log.info("creation type point de vente ...");

		Thread.sleep(1000L);
		TypePointVente typePointVente = new TypePointVente();

		if (!"".equals(model.getCode()) && !"".equals(model.getLibelle())) {

			BeanUtils.copyProperties(model, typePointVente);
			return modelMapper.map(typePointVenteRepository.save(typePointVente),TypePointVenteModelResponse.class);

		} else {
			log.error("la creation de type point de vente est erroné");
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"la creation de type point de vente est erroné");
		}
	}

	@Override
	@Async("asyncExecutor")
	public TypePointVenteModelResponse updateTypePointVente(CreateAndUpdateTypePointVente model, String typePointVenteId) throws InterruptedException, ExecutionException{
		log.info("modifier le  Type de point de vente avec Id : {}", typePointVenteId);

		Thread.sleep(1000L);
		if (!"".equals(model.getCode()) && !"".equals(model.getLibelle())) {

			Optional<TypePointVente> typePointVenteOpt = typePointVenteRepository.findById(typePointVenteId);

			if (typePointVenteOpt.isPresent()) {
				TypePointVente typePrestation = typePointVenteOpt.get();
				BeanUtils.copyProperties(model, typePrestation);
				return modelMapper.map(typePointVenteRepository.save(typePrestation),TypePointVenteModelResponse.class);

			} else {
				log.error("Error updating Type point vente with Id ({}): No type point vente with the given Id",
						typePointVenteId);
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Error updating Type point vente : No type point vente with the given Id");
			}
		} else {
			log.error("Error updating Type point vente with Id ({}): code and libelle are required", typePointVenteId);
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Error creating Type point vente : code and libelle are required");
		}
	}

	@Override
	@Async("asyncExecutor")
	public TypePointVenteModelResponse getTypePointVente(String typePointVenteId) throws InterruptedException, ExecutionException{
		log.info("getting type point vente with Id {}", typePointVenteId);

		Thread.sleep(1000L);
		Optional<TypePointVente> typePointVenteOpt = typePointVenteRepository.findById(typePointVenteId);

		if (typePointVenteOpt.isPresent()) {
			return modelMapper.map(typePointVenteOpt.get(),TypePointVenteModelResponse.class);
		} else {
			log.error("aucun type de point vente avec ce Id: {}", typePointVenteId);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"aucun type de point vente avec ce Id");
		}
	}

	@Override
	@Async("asyncExecutor")
	public Page<TypePointVenteModelResponse> getTypesPointVente(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {
		log.info("Getting types vente");

		Thread.sleep(1000L);
		return typePointVenteRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, TypePointVenteModelResponse.class));

	}

}