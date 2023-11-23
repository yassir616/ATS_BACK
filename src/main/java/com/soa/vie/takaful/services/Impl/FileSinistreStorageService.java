package com.soa.vie.takaful.services.Impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.text.ParseException;

import org.modelmapper.ModelMapper;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.PrestationSinistre;
import com.soa.vie.takaful.entitymodels.FileToUploadSinistre;
import com.soa.vie.takaful.entitymodels.PieceJointe;
import com.soa.vie.takaful.repositories.ISinistreRepository;
import com.soa.vie.takaful.repositories.IFileSinistreRepository;
import com.soa.vie.takaful.repositories.IPieceJointeRepository;
import com.soa.vie.takaful.requestmodels.FileSinistreRequestModel;
import com.soa.vie.takaful.services.IFileSinistreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;


import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class FileSinistreStorageService implements IFileSinistreService {

  @Autowired
  private IFileSinistreRepository fileSinistreDBRepository;
  @Autowired
  private IPieceJointeRepository pieceJointeRepos;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ISinistreRepository sinistreRepos;

  public FileToUploadSinistre store(MultipartFile file, FileSinistreRequestModel fileRequest) throws IOException {

    Optional<PrestationSinistre> prestation_id = sinistreRepos.findById(fileRequest.getPrestationId());
    PrestationSinistre prestationSinistre = prestation_id.get();
    Optional<PieceJointe> pieceJointe_id = pieceJointeRepos.findById(fileRequest.getPieceJointeId());
    PieceJointe pieceJointe = pieceJointe_id.get();
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    boolean is_received = true;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = dateFormat.format(new Date());
    Date dateReception = null;
    try {
        dateReception = dateFormat.parse(formattedDate);
    } catch (ParseException e) {
        log.info("erreur format date");
    }
    FileToUploadSinistre fileSinistreDb = new FileToUploadSinistre(fileName, file.getContentType(), file.getBytes(),pieceJointe, prestationSinistre,is_received,dateReception);
    log.info("FileSinistreDb: {}", fileSinistreDb);
    return fileSinistreDBRepository.save(fileSinistreDb);
  }


  public List<FileToUploadSinistre> uploadedFilesByPrestationId(String prestationId) 
          throws InterruptedException, ExecutionException {
    //List<FileToUploadSinistre> pieces_jointes= this.fileSinistreDBRepository.findByPrestationId(pre); 
    //return fileSinistreDBRepository.findAllByPrestationId();
    Optional<PrestationSinistre> prestationSinistreOptional = sinistreRepos.findById(prestationId);
    if (!prestationSinistreOptional.isPresent()) {
      throw new NoSuchElementException("Cette prestation sinistre n'existe pas ");
    } else {

    
        //PrestationSinistre prestationSinistre = prestationSinistreOptional.get();
        return fileSinistreDBRepository.findAllByPrestationId(prestationId).stream()
        .map(o -> modelMapper.map(o, FileToUploadSinistre.class))
        .collect(Collectors.toList());  
    }

  }

  @Override
  public void deleteFile(String id) {
        fileSinistreDBRepository.deleteById(id);
    }




}
