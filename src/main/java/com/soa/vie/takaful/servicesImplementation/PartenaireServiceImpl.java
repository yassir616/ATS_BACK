package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Partenaire;
import com.soa.vie.takaful.repositories.IPartenaireRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePartenaire;
import com.soa.vie.takaful.responsemodels.PartenaireResponseModel;
import com.soa.vie.takaful.services.IPartenaireService;
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
public class PartenaireServiceImpl implements IPartenaireService {

	@Autowired
	private IPartenaireRepository partenaireRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public PartenaireResponseModel createPartenaire(CreateAndUpdatePartenaire partenaire) throws InterruptedException, ExecutionException{
		log.info("creation partenaire ...");
		Thread.sleep(1000L);
		Partenaire addpartenaire = new Partenaire();
		String code = partenaire.getCode();
		Optional<Partenaire> partenaireexists = partenaireRepository.findByCode(code);

		if (partenaireexists.isPresent()) {
			log.info("partenaire already exist");
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "ce partenaire existe déja");
		} else {
			BeanUtils.copyProperties(partenaire, addpartenaire);
			addpartenaire = this.partenaireRepository.save(addpartenaire);
		}
		return modelMapper.map(addpartenaire, PartenaireResponseModel.class);
		// return addpartenaire;

	}

	@Override
	@Async("asyncExecutor")
	public PartenaireResponseModel updatePartenaire(String partenaireId, CreateAndUpdatePartenaire partenaireModel) throws InterruptedException, ExecutionException{
		Thread.sleep(1000L);
		Optional<Partenaire> partenaireOptional = partenaireRepository.findById(partenaireId);
		if (!partenaireOptional.isPresent()) {
			log.info("no partenaire with the id : " + partenaireId);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ce ID n'existe pas");
		}
		Partenaire partenaireEntity = partenaireOptional.get();
		partenaireEntity.setCode(partenaireModel.getCode());
		partenaireEntity.setSiegeSocial(partenaireModel.getSiegeSocial());
		partenaireEntity.setRaisonSocial(partenaireModel.getRaisonSocial());
		partenaireEntity.setNumeroCompte(partenaireModel.getNumeroCompte());
		partenaireEntity.setTypePartenaire(partenaireModel.getTypePartenaire());
		partenaireEntity.setFraisAcquisition(partenaireModel.getFraisAcquisition());
		partenaireEntity.setTva(partenaireModel.getTva());
		partenaireEntity.setTelephone(partenaireModel.getTelephone());

		return modelMapper.map(partenaireRepository.save(partenaireEntity), PartenaireResponseModel.class);
	}

	@Override
	@Async("asyncExecutor")
	public Page<PartenaireResponseModel> getPartenaires(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException{
		Thread.sleep(1000L);
		return partenaireRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
				.map(o -> modelMapper.map(o, PartenaireResponseModel.class));

	}

	@Override
	@Async("asyncExecutor")
	public PartenaireResponseModel getPartenaireById(String id) throws InterruptedException, ExecutionException{
		Thread.sleep(1000L);
		Optional<Partenaire> partenaireOptional = partenaireRepository.findById(id);
		if (!partenaireOptional.isPresent()) {
			log.info("ce ID n'existe pas : " + id);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun partenaire avec ce id");
		} else {

			return modelMapper.map(partenaireOptional.get(), PartenaireResponseModel.class);
		}

	}

	@Override
	@Async("asyncExecutor")
	public PartenaireResponseModel getPartenaireByCode(String code) throws InterruptedException, ExecutionException{
		Thread.sleep(1000L);
		Optional<Partenaire> partenaireOptional = partenaireRepository.findByCode(code);
		if (!partenaireOptional.isPresent()) {
			log.info("ce code n'existe pas : " + code);
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun partenaire avec ce code");
		} else {
			return modelMapper.map(partenaireOptional.get(), PartenaireResponseModel.class);
		}
	}

}
