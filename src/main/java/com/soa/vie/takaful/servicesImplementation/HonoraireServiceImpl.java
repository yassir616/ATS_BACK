package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Honoraire;
import com.soa.vie.takaful.entitymodels.TypeAuxiliaire;
import com.soa.vie.takaful.repositories.IHonoraireRepository;
import com.soa.vie.takaful.repositories.ITypeAuxiliaireRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateHonoraire;
import com.soa.vie.takaful.responsemodels.HonoraireModelResponse;
import com.soa.vie.takaful.services.IHonoraireService;
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
public class HonoraireServiceImpl implements IHonoraireService {

    private static final String ERRORCREATIONHONORAIRE ="la creation d'honoraire est erroné";

	@Autowired
    private IHonoraireRepository honoraireRepository;
    
    @Autowired
    private ITypeAuxiliaireRepository typeAuxiliairerepository;

    @Autowired
    private ModelMapper modelMapper;

	@Override
    @Async("asyncExecutor")
	public HonoraireModelResponse createHonoraire(CreateAndUpdateHonoraire model)throws InterruptedException, ExecutionException {
		log.info("creation honoraire ...");

        Thread.sleep(1000L);
        Honoraire honoraire = new Honoraire();


            Optional<TypeAuxiliaire> type = typeAuxiliairerepository.findById(model.getTypeAuxiliaireId());

            if(type.isPresent()){
            	honoraire.setTypeAuxiliaireHon(type.get());
                BeanUtils.copyProperties(model, honoraire);

                return modelMapper.map(honoraireRepository.save(honoraire), HonoraireModelResponse.class);
            
        } else {
            log.error(ERRORCREATIONHONORAIRE);
            throw new NullPointerException(ERRORCREATIONHONORAIRE);
        }
	}

	@Override
    @Async("asyncExecutor")
	public HonoraireModelResponse updateHonoraire(String id, CreateAndUpdateHonoraire model) throws InterruptedException, ExecutionException{
		  log.info("modifier l'honoraire  avec  Id : {}", id);

          Thread.sleep(1000L);
          Optional<Honoraire> honoraireOpt = honoraireRepository.findById(id);
          Optional<TypeAuxiliaire> typeAuxiliaire = typeAuxiliairerepository.findById(model.getTypeAuxiliaireId());

          if (honoraireOpt.isPresent() && typeAuxiliaire.isPresent()) {
          	Honoraire honoraire = honoraireOpt.get();
          	honoraire.setTypeAuxiliaireHon(typeAuxiliaire.get());
              BeanUtils.copyProperties(model, honoraire);
              return modelMapper.map(honoraireRepository.save(honoraire),HonoraireModelResponse.class);


          } else {
              log.error("la creation d'honoraire avec ce Id ({}): est erroné",
              id);
              throw new NullPointerException(ERRORCREATIONHONORAIRE);
          }
	}

	@Override
    @Async("asyncExecutor")
	public Page<HonoraireModelResponse> getHonoraires(int page, int limit, String sort, String direction)throws InterruptedException, ExecutionException {
		log.info("lister les honoraires");
        Thread.sleep(1000L);
        return honoraireRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, HonoraireModelResponse.class));
	}

}
