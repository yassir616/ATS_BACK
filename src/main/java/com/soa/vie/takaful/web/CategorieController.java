package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdateCatAndSubCatRequestModel;
import com.soa.vie.takaful.responsemodels.CategorieModelResponse;
import com.soa.vie.takaful.services.ICategorieService;

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
public class CategorieController {

    @Autowired
    private ICategorieService categorieService;

    @PostMapping("categorie")
    public CategorieModelResponse createCategorie(@RequestBody CreateUpdateCatAndSubCatRequestModel requestModel) throws InterruptedException, ExecutionException {
        return categorieService.createCategorie(requestModel);
    }

    @PutMapping("categorie/{id}")
    public CategorieModelResponse updateCategorie(@PathVariable String id,
            @RequestBody CreateUpdateCatAndSubCatRequestModel requestModel) throws InterruptedException, ExecutionException {
        return categorieService.updateCategorie(id, requestModel);
    }

    @GetMapping("categorie")
    public Page<CategorieModelResponse> getAllCategorie(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
        return categorieService.getCategories(page, limit, sort, direction);

    }
}