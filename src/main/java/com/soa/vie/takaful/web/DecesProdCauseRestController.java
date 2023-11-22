package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateDecesProduitCauseRestitution;
import com.soa.vie.takaful.responsemodels.DecesProduitCauseRestitutionModelResponse;
import com.soa.vie.takaful.services.IDecesProdCauseRestService;

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
public class DecesProdCauseRestController {
    @Autowired
    private IDecesProdCauseRestService decesProdCauseRestService;

    @PostMapping("decesprodcauserest")
    public DecesProduitCauseRestitutionModelResponse createDecesProduitCauseRestitution(
            @RequestBody CreateAndUpdateDecesProduitCauseRestitution model) throws InterruptedException, ExecutionException {
        return decesProdCauseRestService.createDecesProduitCauseRestitution(model);
    }

    @PutMapping("decesprodcauserest/{id}")
    public DecesProduitCauseRestitutionModelResponse updateDecesProduitCauseRestitution(@PathVariable String id,
            @RequestBody CreateAndUpdateDecesProduitCauseRestitution model) throws InterruptedException, ExecutionException {
        return decesProdCauseRestService.updateDecesProduitCauseRestitution(id,model);
    }

    @GetMapping("decesprodcauserest")
    public Page<DecesProduitCauseRestitutionModelResponse> getAllDecesProduitCauseRestitutions(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
        return decesProdCauseRestService.getDecesProduitCauseRestitutions(page, limit, sort, direction);
    }

}
