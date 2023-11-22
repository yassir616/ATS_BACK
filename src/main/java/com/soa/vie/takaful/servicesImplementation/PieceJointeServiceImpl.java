package com.soa.vie.takaful.servicesImplementation;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.soa.vie.takaful.entitymodels.PieceJointe;
import com.soa.vie.takaful.repositories.IPieceJointeRepository;
import com.soa.vie.takaful.requestmodels.CreateUpdatePieceJointRequestModel;
import com.soa.vie.takaful.responsemodels.PieceJointeResponseModel;
import com.soa.vie.takaful.services.IPieceJointeService;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PieceJointeServiceImpl implements IPieceJointeService {

    @Autowired
    private IPieceJointeRepository pieceJointeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Async("asyncExecutor")
    public PieceJointeResponseModel createPieceJointe(CreateUpdatePieceJointRequestModel requestModel)
            throws InterruptedException, ExecutionException {

        log.info("creating Piece-Jointe ...");
        Thread.sleep(1000L);
        PieceJointe pieceJointe = new PieceJointe();

        BeanUtils.copyProperties(requestModel, pieceJointe);

        return modelMapper.map(pieceJointeRepository.save(pieceJointe), PieceJointeResponseModel.class);

    }

    // @Override
    // @Async("asyncExecutor")
    // public PieceJointeResponseModel updatePieceJointe(String id,
    // CreateUpdatePieceJointRequestModel requestModel)
    // throws InterruptedException, ExecutionException {

    // log.info("updating Piece-jointe with Id : {}", id);
    // Thread.sleep(1000L);
    // Optional<PieceJointe> psOpt = pieceJointeRepository.findById(id);

    // if (psOpt.isPresent()) {
    // PieceJointe pieceJointe = psOpt.get();
    // BeanUtils.copyProperties(requestModel, pieceJointe);

    // return modelMapper.map(pieceJointeRepository.save(pieceJointe),
    // PieceJointeResponseModel.class);

    // } else {
    // log.error("Error updating pieceJointe with Id ({}): please check the given
    // pieceJointe ", id);
    // throw new NullPointerException("Error updating piece jointe, please check the
    // given pieceJointe");
    // }

    // }

    @Override
    @Async("asyncExecutor")
    public Page<PieceJointeResponseModel> getPieceJointesByCode(int page, int limit, String sort, String direction,
            String code) throws InterruptedException, ExecutionException {
        log.info("Getting piece joints");
        Thread.sleep(1000L);

        return pieceJointeRepository
                .findPieceJointsByCode(Pagination.pageableRequest(page, limit, sort, direction), code)
                .map(o -> modelMapper.map(o, PieceJointeResponseModel.class));
    }

    @Override
    @Async("asyncExecutor")
    public List<PieceJointeResponseModel> getAllPiecesJointesByNumeroSinistre(String numeroSinistre)
            throws InterruptedException, ExecutionException {
        return pieceJointeRepository.findPieceJointsByNumeroSinistre(numeroSinistre)
                .stream()
                .map(o -> modelMapper.map(o, PieceJointeResponseModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Async("asyncExecutor")
    public PieceJointeResponseModel updateNecessityPieceJointe(String id,
            CreateUpdatePieceJointRequestModel requestModel)
            throws InterruptedException, ExecutionException {

        log.info("updating Necessity Of Piece-jointe with Id : {}", id);
        Thread.sleep(1000L);
        Optional<PieceJointe> psOpt = pieceJointeRepository.findById(id);

        if (psOpt.isPresent()) {
            PieceJointe pieceJointe = psOpt.get();

            pieceJointe.setNecessity(requestModel.getNecessity());
            // BeanUtils.copyProperties(requestModel, pieceJointe);

            return modelMapper.map(pieceJointeRepository.save(pieceJointe), PieceJointeResponseModel.class);

        } else {
            log.error("Error updating necessity of pieceJointe with Id ({}): please check the given pieceJointe ", id);
            throw new NullPointerException(
                    "Error updating necessity of piece jointe, please check the given pieceJointe");
        }

    }

}