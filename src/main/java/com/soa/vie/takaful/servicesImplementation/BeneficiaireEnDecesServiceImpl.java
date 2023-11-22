package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.BeneficiaireEnDeces;
import com.soa.vie.takaful.repositories.IBeneficiaireEnDecesRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateBeneficiaireEnDeces;
import com.soa.vie.takaful.responsemodels.BeneficiaireEnDecesResponseModel;
import com.soa.vie.takaful.services.IBeneficiaireEnDecesService;
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
public class BeneficiaireEnDecesServiceImpl implements IBeneficiaireEnDecesService {

    @Autowired
    ModelMapper modelMapper;



    @Autowired
    IBeneficiaireEnDecesRepository iBeneficiaireEnDecesRepository;

    @Override
    @Async("asyncExecutor")
    public BeneficiaireEnDecesResponseModel createBeneficiaireEnDeces(CreateAndUpdateBeneficiaireEnDeces model)throws InterruptedException, ExecutionException {
        
        Thread.sleep(1000L);
        try {
            BeneficiaireEnDeces beneficiaireEnDeces = new BeneficiaireEnDeces();
            BeanUtils.copyProperties(model, beneficiaireEnDeces);

            return modelMapper.map(iBeneficiaireEnDecesRepository.save(beneficiaireEnDeces),
                    BeneficiaireEnDecesResponseModel.class);
        } catch (Exception e) {
            log.info("erreur d'enregistrement");
            return null;
        }
    }

    @Override
    @Async("asyncExecutor")
    public BeneficiaireEnDecesResponseModel updateBeneficiaireEnDeces(String id,
            CreateAndUpdateBeneficiaireEnDeces model) throws InterruptedException, ExecutionException{

                Thread.sleep(1000L);
                Optional<BeneficiaireEnDeces> beneficiareEnDecesOpt = iBeneficiaireEnDecesRepository.findById(id);

        try {
            if (beneficiareEnDecesOpt.isPresent()) {
                BeneficiaireEnDeces beneficiaireEnDeces = beneficiareEnDecesOpt.get();
                BeanUtils.copyProperties(model, beneficiaireEnDeces);
                return modelMapper.map(iBeneficiaireEnDecesRepository.save(beneficiaireEnDeces),
                        BeneficiaireEnDecesResponseModel.class);

            } else {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        "Error updating beneficiaire, No beneficiaire with the given Id");
            }

        }

        catch (Exception exception) {
            log.info("Error updating beneficiaire, No beneficiaire with the given Id");

            return null;
        }

    }

    @Override
    @Async("asyncExecutor")
    public void deleteBeneficiaireEnDeces(String id) throws InterruptedException, ExecutionException{
        
        Thread.sleep(1000L);
        Optional<BeneficiaireEnDeces> beneficiareEnDecesOpt = iBeneficiaireEnDecesRepository.findById(id);

        try {
            if (beneficiareEnDecesOpt.isPresent()) {
                iBeneficiaireEnDecesRepository.delete(beneficiareEnDecesOpt.get());

            } else {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        "Error deleting beneficiaire, No beneficiaire with the given Id");
            }

        }
        catch (Exception exception) {
            log.info("Error deleting beneficiaire, No beneficiaire with the given Id");

        }
    }

    @Override
    @Async("asyncExecutor")
    public Page<BeneficiaireEnDecesResponseModel> getBeneficiairesEnDeces(int page, int limit, String sort,
            String direction)throws InterruptedException, ExecutionException {
              
                Thread.sleep(1000L);
                try {
            return iBeneficiaireEnDecesRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                    .map(o -> modelMapper.map(o, BeneficiaireEnDecesResponseModel.class));

        } catch (Exception e) {

            log.info("Erreur de recuperation des benefiares.");
            return null;
        }
    }

    @Override
    @Async("asyncExecutor")
    public BeneficiaireEnDecesResponseModel getBeneficiaireEnDecesById(String id)throws InterruptedException, ExecutionException {
        Thread.sleep(1000L);
        try {
            Optional<BeneficiaireEnDeces> beneficiaireEnDecesOptional = iBeneficiaireEnDecesRepository.findById(id);

            if (beneficiaireEnDecesOptional.isPresent()) {
                return modelMapper.map(beneficiaireEnDecesOptional.get(), BeneficiaireEnDecesResponseModel.class);
            } else {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "This BeneficiaireEndeces Not found");
            }

        } catch (HttpClientErrorException e) {
            return null;

        }
    }
}
