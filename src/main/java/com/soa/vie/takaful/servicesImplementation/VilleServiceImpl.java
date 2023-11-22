package com.soa.vie.takaful.servicesImplementation;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Ville;
import com.soa.vie.takaful.repositories.IVilleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VilleServiceImpl {
    

    @Autowired
    private IVilleRepository villeRepository;

    public List<Ville> getAllVilleList(){
        return villeRepository.findAll();
    }
}
