package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateBeneficiaireEnDeces;
import com.soa.vie.takaful.responsemodels.BeneficiaireEnDecesResponseModel;
import com.soa.vie.takaful.services.IBeneficiaireEnDecesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/private")
public class BeneficiaireEnDecesController {

    @Autowired
    private IBeneficiaireEnDecesService beneficiaireEnDecesService;

    @PostMapping("beneficiaireendeces")
    public BeneficiaireEnDecesResponseModel createBeneficiaireEnDeces(@RequestBody CreateAndUpdateBeneficiaireEnDeces requestModel) throws InterruptedException, ExecutionException {
        return beneficiaireEnDecesService.createBeneficiaireEnDeces(requestModel);
    }

    @PutMapping("beneficiaireendeces/{id}")
    public BeneficiaireEnDecesResponseModel updateBeneficiaireEnDeces(@PathVariable String id,
                                            @RequestBody CreateAndUpdateBeneficiaireEnDeces requestModel) throws InterruptedException, ExecutionException {
        return beneficiaireEnDecesService.updateBeneficiaireEnDeces(id, requestModel);
    }

    @GetMapping(path = "beneficiaireendeces")
    public Page<BeneficiaireEnDecesResponseModel> getAllBeneficiairesEnDeces(@RequestParam("page") int page,
                                                  @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
                                                  @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
        return beneficiaireEnDecesService.getBeneficiairesEnDeces(page, limit, sort, direction);

    }

    @GetMapping("beneficiaireendeces/{id}")
    public BeneficiaireEnDecesResponseModel getBeneficiairesEnDecesById(@PathVariable String id) throws InterruptedException, ExecutionException {
        return beneficiaireEnDecesService.getBeneficiaireEnDecesById(id);
    }

    @DeleteMapping("beneficiaireendeces/{id}")
    public void deleteBeneficiaireEnDeces (@PathVariable String id) throws InterruptedException, ExecutionException {
        beneficiaireEnDecesService.deleteBeneficiaireEnDeces(id);
    }

}

