package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePointVente;
import com.soa.vie.takaful.responsemodels.TypePointVenteModelResponse;
import com.soa.vie.takaful.services.ITypePointVenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/private")
public class TypeVenteController {
    @Autowired
    private ITypePointVenteService typePointVenteService;



    @PostMapping("typepointvente")
    public TypePointVenteModelResponse createTypePointVente(@RequestBody CreateAndUpdateTypePointVente model) throws InterruptedException, ExecutionException {
        return typePointVenteService.createTypePointVente(model);
    }

    @PutMapping("typepointvente/{id}")
    public TypePointVenteModelResponse updateTypePointVente(@PathVariable String id,
            @RequestBody CreateAndUpdateTypePointVente model) throws InterruptedException, ExecutionException {
        return typePointVenteService.updateTypePointVente(model,id);
    }

    @GetMapping("typepointvente/{id}")
    public TypePointVenteModelResponse getTypePointVente(@PathVariable String id) throws InterruptedException, ExecutionException {
        return typePointVenteService.getTypePointVente(id);
    }

    @GetMapping(path = "typepointventes")
    public Page<TypePointVenteModelResponse> getAllTypePointVente(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {

        return typePointVenteService.getTypesPointVente(page,limit,sort,direction);
    }

}
