package com.soa.vie.takaful.servicesImplementation;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.repositories.IProduitRepository;
import com.soa.vie.takaful.responsemodels.ProduitModelResponse;
import com.soa.vie.takaful.services.IProduitService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ProduitServiceImpl implements IProduitService {

    @Autowired
    private IProduitRepository produitRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public ProduitModelResponse getProduitByCode(String code)
            throws InterruptedException, ExecutionException {

        Thread.sleep(1000L);
        Optional<Produit> produitEntity = produitRepository.findByCode(code);
        if (produitEntity.isPresent()) {
            return modelMapper.map(produitEntity.get(), ProduitModelResponse.class);
        } else {

            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "il y'a aucun produit avec ce code");
        }
    }

}
