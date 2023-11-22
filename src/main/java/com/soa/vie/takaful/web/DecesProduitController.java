package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateDecesProduit;
import com.soa.vie.takaful.requestmodels.UpdateDecesProduit;
import com.soa.vie.takaful.responsemodels.DecesProduitResponseModel;
import com.soa.vie.takaful.responsemodels.ProduitModelResponse;
import com.soa.vie.takaful.services.IDecesProduitService;

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
public class DecesProduitController {
    @Autowired
    private IDecesProduitService decesProduitService;


    @PostMapping("decesproduit")
    public ProduitModelResponse createDecesProduit(@RequestBody CreateAndUpdateDecesProduit requestModel) throws InterruptedException, ExecutionException {
        return decesProduitService.createDecesProduit(requestModel);
    }

    @PutMapping("decesproduit/{id}")
    public ProduitModelResponse updateDecesProduit(@PathVariable String id,
            @RequestBody UpdateDecesProduit requestModel) throws InterruptedException, ExecutionException {
        return decesProduitService.updateDecesProduit(id, requestModel);
    }

    @GetMapping("decesproduit")
    public Page<DecesProduitResponseModel> getAllDecesProduits(@RequestParam("page") int page,
                                                               @RequestParam(name = "limit", defaultValue = "" + Integer.MAX_VALUE) int limit,
                                                               @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {

        return decesProduitService.getDecesProduits(page, limit, sort, direction);
    }

}
