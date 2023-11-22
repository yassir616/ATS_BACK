package com.soa.vie.takaful.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateUpdatePieceJointRequestModel;
import com.soa.vie.takaful.responsemodels.PieceJointeResponseModel;

import org.springframework.data.domain.Page;

public interface IPieceJointeService {

        public PieceJointeResponseModel createPieceJointe(CreateUpdatePieceJointRequestModel requestModel)
                        throws InterruptedException, ExecutionException;

        // public ResponseModel updatePieceJointe(String id,
        // CreateUpdatePieceJointRequestModel requestModel)
        // throws InterruptedException, ExecutionException;

        public Page<PieceJointeResponseModel> getPieceJointesByCode(int page, int limit, String sort, String direction,
                        String code) throws InterruptedException, ExecutionException;

        public List<PieceJointeResponseModel> getAllPiecesJointesByNumeroSinistre(String numeroSinistre)
                        throws InterruptedException, ExecutionException;

        public PieceJointeResponseModel updateNecessityPieceJointe(String id,
                        CreateUpdatePieceJointRequestModel requestModel)
                        throws InterruptedException, ExecutionException;
}