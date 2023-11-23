package com.soa.vie.takaful.services.Impl;

import com.soa.vie.takaful.repositories.IFluxRepository;
import com.soa.vie.takaful.requestmodels.CreateAndUpdateFlux;
import com.soa.vie.takaful.requestmodels.SearchFlux;
import com.soa.vie.takaful.responsemodels.FluxResponseModel;
import com.soa.vie.takaful.services.IFluxService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.soa.vie.takaful.entitymodels.Flux;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FluxServiceImpl implements IFluxService{

    @Autowired
    private IFluxRepository fluxRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Async("asyncExecutor")
    public List<FluxResponseModel> searchFichiersRecus(SearchFlux searchFlux)throws InterruptedException, ExecutionException {
      
        Thread.sleep(1000L);
        return fluxRepository.findByProduitIdAndPartenaireIdAndDateTraitementAndTypeFluxIdAndEtat(searchFlux.getProduit(),searchFlux.getPartenaire(),searchFlux.getDateTraitement(),searchFlux.getTypeDeFlux(),searchFlux.getEtat()).stream()
                .map(o -> modelMapper.map(o, FluxResponseModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Async("asyncExecutor")
    public FluxResponseModel addFlux(CreateAndUpdateFlux createAndUpdateFlux)throws InterruptedException, ExecutionException{

        Thread.sleep(1000L);
        Flux flux = new Flux();
        BeanUtils.copyProperties(createAndUpdateFlux,flux);
        try {
            return modelMapper.map(fluxRepository.save(flux),FluxResponseModel.class);
        } catch (Exception e) {
        return  null;
        }
    }
}
