package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateReassuranceModel;
import com.soa.vie.takaful.requestmodels.CreateallAcceptationTestKindModelRequest;
import com.soa.vie.takaful.responsemodels.AcceptationReassuranceModelResponse;
import com.soa.vie.takaful.services.IAcceptationReassuranceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/private")
public class AcceptationReassuranceController {

    @Autowired
    private IAcceptationReassuranceService acceptationReassurance;

    @PostMapping("acceptation-reassurance")
    public AcceptationReassuranceModelResponse createAcceptationReassurance(
            @RequestBody CreateallAcceptationTestKindModelRequest model)
            throws InterruptedException, ExecutionException {
        return acceptationReassurance.createAcceptationReassurance(model);
    }

    @GetMapping("acceptation-reassurance")
    public List<AcceptationReassuranceModelResponse> getByAcceptation(@RequestParam String acceptation)
            throws InterruptedException, ExecutionException {
        return acceptationReassurance.acceptationReassuranceByAcceptation(acceptation);

    }

    @PutMapping("acceptation-reassurance")
    public AcceptationReassuranceModelResponse updateAcceptationReassurance(@RequestBody CreateReassuranceModel model,
            String id) throws InterruptedException, ExecutionException {
        return acceptationReassurance.updateAcceptationReassurance(model, id);
    }

}