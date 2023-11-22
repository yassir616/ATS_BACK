package com.soa.vie.takaful.servicesImplementation;


import com.soa.vie.takaful.entitymodels.*;
import com.soa.vie.takaful.repositories.IContractRepository;
import com.soa.vie.takaful.repositories.IPrestationRachatTotalRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdatePrestationRachatTotal;
import com.soa.vie.takaful.responsemodels.PrestationRachatTotalModelResponse;
import com.soa.vie.takaful.services.IPrestationRachatTotalService;
import com.soa.vie.takaful.util.PrestationStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class PrestationRachatTotalServiceImpl implements IPrestationRachatTotalService{


    @Autowired
    private IContractRepository contractRepository;

    @Autowired
    private IPrestationRachatTotalRepository prestationRachatTotalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public PrestationRachatTotalModelResponse createPrestationRachatTotal(CreateAndUpdatePrestationRachatTotal requestModel) throws InterruptedException,ExecutionException{
        log.info("creation du prestation Rachat Total ...");
        Thread.sleep(1000L);
        PrestationRachatTotal prestationRachatTotal = new PrestationRachatTotal();

        Optional<Contract> contrat = contractRepository.findById(requestModel.getContratId());

        if (contrat.isPresent()) {
            prestationRachatTotal.setContrat(contrat.get());
            prestationRachatTotal.setStatus(PrestationStatusEnum.EN_COURS);

            BeanUtils.copyProperties(requestModel, prestationRachatTotal);
            return modelMapper.map(prestationRachatTotalRepository.save(prestationRachatTotal), PrestationRachatTotalModelResponse.class);


        } else {
            log.error("Creation de la prestation rachat total erronée : Contrat n'existe pas ");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"Creation de la prestation rachat total erronée : Contrat n'existe pas ");
        }    }

    @Override
    @Async("asyncExecutor")
    public List<PrestationRachatTotalModelResponse> getPrestationsRachatTotalbyContrat(String contratId) throws InterruptedException,ExecutionException{
        Thread.sleep(1000L);

        return prestationRachatTotalRepository.findByContratId(contratId)
                .stream()
                .map(o -> modelMapper.map(o,PrestationRachatTotalModelResponse.class))
                .collect(Collectors.toList());    }

    @Override
    @Async("asyncExecutor")
    public PrestationRachatTotalModelResponse updatePrestationRachatTotal(CreateAndUpdatePrestationRachatTotal model, String id)throws InterruptedException,ExecutionException {
        log.info("modifier le sinistre avec Id : {}", id);

        Optional<PrestationRachatTotal> prestationRachatTotalOptional = prestationRachatTotalRepository.findById(id);


        if (prestationRachatTotalOptional.isPresent()) {
            PrestationRachatTotal prestationRachatTotal = prestationRachatTotalOptional.get();
            BeanUtils.copyProperties(model, prestationRachatTotal);
            return modelMapper.map(prestationRachatTotalRepository.save(prestationRachatTotal),PrestationRachatTotalModelResponse.class);

        } else {
            log.error("la modification de prestation rachat total avec ce Id ({}): est erronée", id);
            throw new NullPointerException("la modification de prestation rachat total avec ce Id est erronée");
        }    }

    @Override
    @Async("asyncExecutor")
    public void deletePrestationRachatTotal(String id) throws InterruptedException,ExecutionException{
        Thread.sleep(1000L);
        prestationRachatTotalRepository.deleteById(id);
    }
}
