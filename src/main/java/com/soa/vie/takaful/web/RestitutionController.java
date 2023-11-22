
package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateRestitution;
import com.soa.vie.takaful.responsemodels.RestitutionResponseModel;
import com.soa.vie.takaful.services.IRestitutionService;

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
public class RestitutionController {

    @Autowired
    private IRestitutionService restitutionService;
   ;

    @PostMapping("restitution")
    public RestitutionResponseModel createRestitution(@RequestBody CreateAndUpdateRestitution restitution) throws InterruptedException, ExecutionException {

        return restitutionService.createRestitution(restitution);
    }

    @GetMapping("restitution/{id}")
    public RestitutionResponseModel getRestitution(@PathVariable String id) throws InterruptedException, ExecutionException {
        return restitutionService.getRestitutionById(id);
    }

    @PutMapping("restitution/{id}")
    public RestitutionResponseModel updateRestitution(@PathVariable String id, @RequestBody CreateAndUpdateRestitution typeModel) throws InterruptedException, ExecutionException {

        return restitutionService.updateRestitution(id, typeModel);
    }

    @GetMapping("restitutions")
    public Page<RestitutionResponseModel> getAllRestitution(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {

        return restitutionService.getRestitution(page, limit, sort, direction);
    }

}