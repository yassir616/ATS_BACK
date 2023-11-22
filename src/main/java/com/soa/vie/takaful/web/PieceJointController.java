package com.soa.vie.takaful.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdatePieceJointRequestModel;
import com.soa.vie.takaful.responsemodels.PieceJointeResponseModel;
import com.soa.vie.takaful.services.IPieceJointeService;

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
public class PieceJointController {

    @Autowired
    private IPieceJointeService piecejointeService;

    @PostMapping("piecejointe")
    public PieceJointeResponseModel createPieceJointe(@RequestBody CreateUpdatePieceJointRequestModel requestModel)
            throws InterruptedException, ExecutionException {
        return piecejointeService.createPieceJointe(requestModel);

    }

    // @PutMapping("piecejointe/{id}")
    // public PieceJointeResponseModel updatePieceJointe(@PathVariable String id,
    // @RequestBody CreateUpdatePieceJointRequestModel requestModel)
    // throws InterruptedException, ExecutionException {
    // return piecejointeService.updatePieceJointe(id, requestModel);

    // }

    @GetMapping("piecejointe/{code}")
    public Page<PieceJointeResponseModel> getAllPieceJointe(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction, @PathVariable String code)
            throws InterruptedException, ExecutionException {

        return piecejointeService.getPieceJointesByCode(page, limit, sort, direction, code);
    }

    @GetMapping("piecejointe/NumeroSinistre/{numeroSinistre}")
    public List<PieceJointeResponseModel> getAllPiecesJointesByNumeroSinistre(@PathVariable String numeroSinistre)
            throws InterruptedException, ExecutionException {

        return piecejointeService.getAllPiecesJointesByNumeroSinistre(numeroSinistre);
    }

    @PutMapping("updateNecessityPieceJointe/{id}")
    public PieceJointeResponseModel updateNecessityPieceJointe(@PathVariable String id,
            @RequestBody CreateUpdatePieceJointRequestModel requestModel)
            throws InterruptedException, ExecutionException {
        return piecejointeService.updateNecessityPieceJointe(id, requestModel);
    }
}