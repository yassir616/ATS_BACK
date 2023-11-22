package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Risque;
import com.soa.vie.takaful.repositories.IRisqueRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateRisqueRequestModel;
import com.soa.vie.takaful.responsemodels.RisqueResponseModel;
import com.soa.vie.takaful.services.IRisqueService;
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
public class RisqueServiceImpl implements IRisqueService {

    @Autowired
    private IRisqueRepository risqueRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public RisqueResponseModel createRisque(CreateAndUpdateRisqueRequestModel requestModel)
            throws InterruptedException, ExecutionException {

        log.info("creating Risque ...");
        Thread.sleep(1000L);

        Risque risque = new Risque();

        if (!"".equals(requestModel.getCode()) && !"".equals(requestModel.getLibelle())
                && !"".equals(requestModel.getIcon()) && !"".equals(requestModel.getBundle())) {

            BeanUtils.copyProperties(requestModel, risque);
            return modelMapper.map(risqueRepository.save(risque), RisqueResponseModel.class);
        } else {
            log.error("Error creating Risque you need to insert all required values");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Error creating Risque you need to insert all required values");
        }
    }

    @Override
    @Async("asyncExecutor")
    public RisqueResponseModel updateRisque(String risqueId, CreateAndUpdateRisqueRequestModel requestModel)
            throws InterruptedException, ExecutionException {

        log.info("updating Type Prestation with Id : {}", risqueId);
        Thread.sleep(1000L);

        if (!"".equals(requestModel.getCode()) && !"".equals(requestModel.getLibelle())
                && !"".equals(requestModel.getIcon()) && !"".equals(requestModel.getBundle())) {

            Optional<Risque> risqueOpt = risqueRepository.findById(risqueId);

            if (risqueOpt.isPresent()) {
                Risque risque = risqueOpt.get();
                BeanUtils.copyProperties(requestModel, risque);
                return modelMapper.map(risqueRepository.save(risque), RisqueResponseModel.class);

            } else {
                log.error("Error updating risque with Id ({}): No risque with the given Id", risqueId);
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        "Error updating risque, No risque with the given Id");
            }
        } else {
            log.error("Error updating risque with Id ({}): please insert required values", risqueId);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Error updating risque, please insert required values");
        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<RisqueResponseModel> getAllRisque(int page, int limit, String sort, String direction, String theme)
            throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        log.info("Getting risques");
        return risqueRepository.findAllRisqueByTheme(Pagination.pageableRequest(page,
                limit, sort, direction), theme)
                .map(o -> modelMapper.map(o, RisqueResponseModel.class));
    }

    @Override
    @Async("asyncExecutor")
    public RisqueResponseModel risqueById(String id) throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        Optional<Risque> risqueOpt = risqueRepository.findById(id);
        if (!risqueOpt.isPresent()) {
            log.info("ce ID n'existe pas : " + id);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun partenaire avec ce id");
        } else {
            return modelMapper.map(risqueOpt.get(), RisqueResponseModel.class);
        }

    }

}