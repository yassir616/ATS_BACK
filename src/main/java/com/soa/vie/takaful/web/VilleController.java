package com.soa.vie.takaful.web;

import java.util.List;

import com.soa.vie.takaful.entitymodels.Ville;
import com.soa.vie.takaful.servicesImplementation.VilleServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class VilleController {
    

    @Autowired
    private VilleServiceImpl villeServiceImpl;
    
    @GetMapping("villes")
    public List<Ville> getVilles() 
    {
        return villeServiceImpl.getAllVilleList();
    }
}
