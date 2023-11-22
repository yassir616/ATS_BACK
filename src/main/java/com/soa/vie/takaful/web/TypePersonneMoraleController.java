package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.TypePersonneMoraleModelResponse;
import com.soa.vie.takaful.services.ITypePersonneMoraleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class TypePersonneMoraleController {

    @Autowired
    private ITypePersonneMoraleService typePersonneMoraleService;

    @GetMapping(path = "type-personne-morale")
    public List<TypePersonneMoraleModelResponse> getAllPersonneMorales()
            throws InterruptedException, ExecutionException {
        return typePersonneMoraleService.getTypesPersonneMorales();
    }

    @GetMapping("/typePersonneMoraleByLibelle")
    public TypePersonneMoraleModelResponse getTypePersonneMoralByLibelle(@RequestParam("libelle") String libelle)
            throws InterruptedException, ExecutionException {
        return typePersonneMoraleService.getTypePersonneMoraleByLibelle(libelle);

    }
}