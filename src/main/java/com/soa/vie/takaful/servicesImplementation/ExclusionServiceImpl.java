package com.soa.vie.takaful.servicesImplementation;



import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Exclusion;
import com.soa.vie.takaful.repositories.IExclusionRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateExclusion;
import com.soa.vie.takaful.responsemodels.ExclusionResponseModel;
import com.soa.vie.takaful.services.IExclusionService;
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
public class ExclusionServiceImpl implements IExclusionService {
	
    @Autowired
    private IExclusionRepository exclusionRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Async("asyncExecutor")
	public ExclusionResponseModel createExclusion(CreateAndUpdateExclusion model)throws InterruptedException, ExecutionException {
		log.info("creation d'exclusion ...");

		Thread.sleep(1000L);
		Exclusion exclusionreturn = new Exclusion();
	        String exclusion = model.getExclusionNom();

	        Exclusion exclusionexists = exclusionRepository.findByExclusionNom(exclusion);

	        if (exclusionexists != null) {
	            log.info("exclusion esxiste deja");
	            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"exclusion esxiste deja");
	        }

	        else {

	            BeanUtils.copyProperties(model, exclusionreturn);

	            exclusionreturn = exclusionRepository.save(exclusionreturn);

	        }

	        return modelMapper.map(exclusionreturn,ExclusionResponseModel.class);

	}

	@Override
	@Async("asyncExecutor")
	public ExclusionResponseModel updateExclusion(String id, CreateAndUpdateExclusion model) throws InterruptedException, ExecutionException{
	
		Thread.sleep(1000L);
		Optional<Exclusion>exclusionOptional = exclusionRepository.findById(id);
	        if (!exclusionOptional.isPresent()) {
	            log.info("y'a aucune exclusion avec ce id: " + id);
	            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"y'a aucune exclusion avec cet id: " + id);
	        }
			Exclusion exclusionEntity = exclusionOptional.get();
	        exclusionEntity.setExclusionNom(model.getExclusionNom());


	        return modelMapper.map(exclusionRepository.save(exclusionEntity),ExclusionResponseModel.class);
	}

	@Override
	@Async("asyncExecutor")
	public Page<ExclusionResponseModel> getExclusions(int page, int limit, String sort, String direction,String famille) throws InterruptedException, ExecutionException{
		 log.info("lister les exclusions");

		 Thread.sleep(1000L);
		return exclusionRepository.findByFamille(Pagination.pageableRequest(page, limit, sort, direction),famille)
				.map(o -> modelMapper.map(o, ExclusionResponseModel.class));
	}
	


}
