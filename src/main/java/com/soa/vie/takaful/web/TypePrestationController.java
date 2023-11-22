package com.soa.vie.takaful.web;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateTypePrestationRequestModel;
import com.soa.vie.takaful.responsemodels.TypePrestationModelResponse;
import com.soa.vie.takaful.services.ITypePrestationService;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
public class TypePrestationController {

    @Autowired
    private ITypePrestationService typePrestationService;


    @PostMapping("typeprestation")
    public TypePrestationModelResponse createTypePrestation(@RequestBody CreateAndUpdateTypePrestationRequestModel model) throws InterruptedException, ExecutionException {
        return typePrestationService.createTypePrestation(model);
    }

    @PutMapping("typeprestation/{id}")
    public TypePrestationModelResponse updateTypePrestation(@PathVariable String id,
            @RequestBody CreateAndUpdateTypePrestationRequestModel model) throws InterruptedException, ExecutionException {
        return typePrestationService.updateTypePrestation(model,id);

    }

    @GetMapping("typeprestation/{id}")
    public TypePrestationModelResponse getTypePrestation(@PathVariable String id) throws InterruptedException, ExecutionException {
        return typePrestationService.getTypePrestation(id);
    }

    @GetMapping("typeprestationFamille/{id}")
    public List<TypePrestationModelResponse> getListPrestation(@PathVariable String id) throws InterruptedException, ExecutionException {
    	
    	System.out.println(id);
        return typePrestationService.getListPrestation(id);
    }
    
    @GetMapping(path = "typeprestation")
    public Page<TypePrestationModelResponse> getAllPrestations(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
        return typePrestationService.getTypesPrestation(page,limit,sort,direction);
    }
}