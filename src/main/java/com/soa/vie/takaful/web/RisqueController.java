package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateRisqueRequestModel;
import com.soa.vie.takaful.responsemodels.RisqueResponseModel;
import com.soa.vie.takaful.services.IRisqueService;

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
public class RisqueController {

    @Autowired
    private IRisqueService risqueService;

    @PostMapping("risque")
    public RisqueResponseModel createRisque(@RequestBody CreateAndUpdateRisqueRequestModel requestModel)
            throws InterruptedException, ExecutionException {
        return risqueService.createRisque(requestModel);
    }

    @PutMapping("risque/{id}")
    public RisqueResponseModel updateRisque(@PathVariable String id,
            @RequestBody CreateAndUpdateRisqueRequestModel requestModel)
            throws InterruptedException, ExecutionException {
        return risqueService.updateRisque(id, requestModel);
    }

    @GetMapping(path = "risque")
    public Page<RisqueResponseModel> getAllRisque(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction,
            @RequestParam("theme") String theme) throws InterruptedException, ExecutionException {
        return risqueService.getAllRisque(page, limit, sort, direction, theme);

    }

    @GetMapping("risque/{id}")
    public RisqueResponseModel getRisqueId(@PathVariable String id) throws InterruptedException, ExecutionException {
        return risqueService.risqueById(id);
    }
}