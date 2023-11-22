package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Acceptation;
import com.soa.vie.takaful.repositories.IAcceptationRepository;
import com.soa.vie.takaful.responsemodels.AcceptationModelResponse;
import com.soa.vie.takaful.services.IAcceptationService;
import com.soa.vie.takaful.util.Constants;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AcceptationServiceImpl implements IAcceptationService {

    @Autowired
    private IAcceptationRepository acceptationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public Page<AcceptationModelResponse> getAcceptations(int page, int limit, String sort, String direction)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        return acceptationRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction))
                .map(o -> modelMapper.map(o, AcceptationModelResponse.class));

    }

    @Override
    @Async("asyncExecutor")
    public Page<AcceptationModelResponse> searchAcceptation(int page, int limit, String sort, String direction,
            String searchBy, String searchFor) throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        if (Constants.PRENOM_ASSURE.equals(searchBy)) {
            return acceptationRepository
                    .findByAssurePrenom(Pagination.pageableRequest(page, limit, sort, direction), searchFor)
                    .map(o -> modelMapper.map(o, AcceptationModelResponse.class));

        } else if (Constants.NOM_ASSURE.equals(searchBy)) {
            return acceptationRepository
                    .findByAssureNom(Pagination.pageableRequest(page, limit, sort, direction), searchFor)
                    .map(o -> modelMapper.map(o, AcceptationModelResponse.class));

        } else if (Constants.CODE_ACCEPTATION.equals(searchBy)) {
            return acceptationRepository
                    .findByCodeStartingWith(Pagination.pageableRequest(page, limit, sort, direction), searchFor)
                    .map(o -> modelMapper.map(o, AcceptationModelResponse.class));

        } else if (Constants.NUM_CONTRAT.equals(searchBy)) {
            return acceptationRepository
                    .findByNumeroContrat(Pagination.pageableRequest(page, limit, sort, direction), searchFor)
                    .map(o -> modelMapper.map(o, AcceptationModelResponse.class));

        }
        throw new IllegalArgumentException("la recherche doit etre efactuer par nom,prenom,Numéro Contrat ou code");

    }

    @Override
    public String getCodeByContratId(String id) {
        // TODO Auto-generated method stub

        return acceptationRepository.findCodeByContratId(id);
    }

    @Override
    public AcceptationModelResponse getAcceptationByContratId(String id) {

        Optional<Acceptation> acceptationOptional = acceptationRepository.findByContratId(id);
        if (!acceptationOptional.isPresent()) {
            throw new IllegalArgumentException("il y'a aucune auxiliaire avec ce id");
        } else {
            return modelMapper.map(acceptationOptional.get(), AcceptationModelResponse.class);

        }

    }

}