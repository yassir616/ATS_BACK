package com.soa.vie.takaful.servicesImplementation;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Avenant;
import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.TypeAvenant;
import com.soa.vie.takaful.repositories.IAvenantRepository;
import com.soa.vie.takaful.responsemodels.AvenantModelResponse;
import com.soa.vie.takaful.services.IAvenantService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AvenantServiceImpl implements IAvenantService {

    @Autowired
    private IAvenantRepository avenantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public List<AvenantModelResponse> getByContratId(String id) throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        List<Avenant> avenantList = avenantRepository.findByContratId(id);
        if (avenantList == null) {
            log.info("ce ID n'existe pas : " + id);
            throw new NoSuchElementException("il y'a aucun avenant avec ce contrat id");
        } else {
            return avenantList.stream().map(o -> modelMapper.map(o, AvenantModelResponse.class))
                    .collect(Collectors.toList());

        }
    }

    @Override
    @Async("asyncExecutor")
    public Avenant createAvenant(Contract contract, Integer numeroAvenant, TypeAvenant type)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        Date now = new Date();

        Avenant avenant = new Avenant();
        avenant.setDateEffet(now);
        avenant.setNumeroAvenant(numeroAvenant);
        avenant.setTypeAvenant(type);
        Avenant avenantReturn = this.avenantRepository.save(avenant);
        this.avenantRepository.updateContratAvenant(contract.getId(), avenant.getId());
        return avenantReturn;
    }

    @Override
    public Avenant createAvenantChangementAdresse(Contract contract, TypeAvenant type) {
        // TODO Auto-generated method stub
        return null;
    }

}
