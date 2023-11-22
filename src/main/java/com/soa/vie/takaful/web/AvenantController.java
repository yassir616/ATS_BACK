package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.AvenantModelResponse;
import com.soa.vie.takaful.services.IAvenantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class AvenantController {

    @Autowired
    private IAvenantService avenantService;

    @GetMapping(path = "avenant/{id}")
    public List<AvenantModelResponse> avenantByContratId(@PathVariable String id) throws InterruptedException, ExecutionException {
        return avenantService.getByContratId(id);
    }
}