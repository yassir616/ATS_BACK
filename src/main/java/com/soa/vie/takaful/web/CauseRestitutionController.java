package com.soa.vie.takaful.web;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdateOneStringRequestModel;
import com.soa.vie.takaful.responsemodels.CauseRestitutionModelResponse;
import com.soa.vie.takaful.services.ICauseRestitutionService;

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
public class CauseRestitutionController {

    @Autowired
    private ICauseRestitutionService causeRestitutionService;

    @PostMapping("causerestitution")
    public CauseRestitutionModelResponse createCauseRestitution(@RequestBody CreateUpdateOneStringRequestModel model) throws InterruptedException, ExecutionException {
        return causeRestitutionService.createCauseRestitution(model);
    }

    @PutMapping("causerestitution/{id}")
    public CauseRestitutionModelResponse updateCauseRestitution(@PathVariable String id,
            @RequestBody CreateUpdateOneStringRequestModel model) throws InterruptedException, ExecutionException {
        return causeRestitutionService.updateCauseRestitution(model, id);
    }

    @GetMapping(path = "causerestitution")
    public Page<CauseRestitutionModelResponse> getCauseRestitution(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException, ExecutionException {
        return causeRestitutionService.getCauseRestitution(page, limit, sort, direction);

    }
}