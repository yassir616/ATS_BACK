package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.CompteBancaire;
import com.soa.vie.takaful.repositories.ICompteBancaire;
import com.soa.vie.takaful.responsemodels.CompteBancaireResponseModel;
import com.soa.vie.takaful.services.ICompteBancaireService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CompteBancaireServiceImpl implements ICompteBancaireService{
	@Autowired
	private ICompteBancaire compteRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public Page<CompteBancaireResponseModel> getCompteBancaire(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {
		log.info("lister les compteBancaire");

		Thread.sleep(1000L);
		return compteRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, CompteBancaireResponseModel.class));
	}

	@Override
	@Async("asyncExecutor")
	public CompteBancaireResponseModel getCompteBancaireById(String idCpt) throws InterruptedException, ExecutionException{
		Thread.sleep(1000L);
		
		Optional<CompteBancaire> compteBancaireOpt = compteRepository.findByPointVenteId(idCpt);
		if (!compteBancaireOpt.isPresent()) {
			log.info("ce ID n'existe pas : " + idCpt);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun partenaire avec ce id");
		} else {
			System.out.println("idCpt :" +idCpt);
			return modelMapper.map(compteBancaireOpt.get(), CompteBancaireResponseModel.class);
		}

	}

}
