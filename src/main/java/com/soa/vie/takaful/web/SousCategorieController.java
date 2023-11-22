package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdateCatAndSubCatRequestModel;
import com.soa.vie.takaful.responsemodels.SousCategorieModelResponse;
import com.soa.vie.takaful.services.ISousCategorieService;
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
public class SousCategorieController {

    @Autowired
    private ISousCategorieService categorieService;

    @PostMapping("souscategorie")
    public SousCategorieModelResponse createSousCategorie(@RequestBody CreateUpdateCatAndSubCatRequestModel requestModel) throws InterruptedException, ExecutionException {
        return categorieService.createSousCategorie(requestModel);
    }

    @PutMapping("souscategorie/{id}")
    public SousCategorieModelResponse updateSousCategorie(@PathVariable String id,
            @RequestBody CreateUpdateCatAndSubCatRequestModel requestModel) throws InterruptedException, ExecutionException {
        return categorieService.updateSousCategorie(id, requestModel);
    }

    @GetMapping("souscategorie")
    public Page<SousCategorieModelResponse> getAllSousCategorie(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
        return categorieService.getSousCategories(page,limit,sort,direction);

    }
}