package com.soa.vie.takaful.web;


import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateRetraiteProduit;
import com.soa.vie.takaful.responsemodels.RetraiteProduitResponseModel;
import com.soa.vie.takaful.services.IRetraiteProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private")
public class RetraitProduitController {


    @Autowired
    IRetraiteProduitService retraiteProduitService;


    @PostMapping("retraitproduit")
    public RetraiteProduitResponseModel createRetraitProduit(@RequestBody CreateAndUpdateRetraiteProduit retraiteProduit) throws InterruptedException, ExecutionException{
        return retraiteProduitService.createRetraiteProduit(retraiteProduit);
    }

    @PutMapping("retraitproduit/{id}")
    public RetraiteProduitResponseModel updateRetraitProduit(@PathVariable String id,
                                                @RequestBody CreateAndUpdateRetraiteProduit retraiteProduit) throws InterruptedException, ExecutionException{
        return retraiteProduitService.updateRetraiteProduit(id, retraiteProduit);
    }

    @GetMapping("retraitproduit")
    public Page<RetraiteProduitResponseModel> getAllRetraitProduits(@RequestParam("page") int page,
                                                       @RequestParam(name = "limit", defaultValue = "" + Integer.MAX_VALUE) int limit,
                                                       @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException{
       return retraiteProduitService.getRetraiteProduits(page,limit,sort,direction);


    }
    @GetMapping("retraitproduit/{id}")
    public RetraiteProduitResponseModel getRetraitProduitById(@PathVariable String id) throws InterruptedException, ExecutionException{
        return retraiteProduitService.getRetraiteProduitById(id);
    }

    @GetMapping("retraitproduit/code/{code}")
    public RetraiteProduitResponseModel getRetraitProduitByCode(@PathVariable String code) throws InterruptedException, ExecutionException{
        return retraiteProduitService.getRetraiteProduitByCode(code);
    }
}
