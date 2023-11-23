package com.soa.vie.takaful.services.Impl;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.Contract;
import com.soa.vie.takaful.entitymodels.FileToUpload;
import com.soa.vie.takaful.repositories.IContractRepository;
import com.soa.vie.takaful.repositories.IFileRepository;
import com.soa.vie.takaful.requestmodels.FileRequestModel;
import com.soa.vie.takaful.services.IFileService;
import com.soa.vie.takaful.util.ContratStatus;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;

@Service
@Transactional
@Slf4j
public class FileStorageService implements IFileService {

  @Autowired
  private IFileRepository fileDBRepository;
  @Autowired
  private IContractRepository contractRepos;

  public FileToUpload store(MultipartFile file, FileRequestModel fileRequest) throws IOException {
    log.info("store function....");
    Optional<Contract> contract_id = contractRepos.findById(fileRequest.getContract_id());
    Contract contrat = contract_id.get();
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    contrat.setStatus(ContratStatus.ACCEPTED);
    FileToUpload fileDb = new FileToUpload(fileName, file.getContentType(), file.getBytes(), contrat);
    return fileDBRepository.save(fileDb);
  }

  public FileToUpload getFile(String id) {
    Optional<FileToUpload> fileOptional = fileDBRepository.findById(id);

    if (fileOptional.isPresent()) {
      return fileOptional.get();
    }
    return null;

  }

  public byte[] getData(String contratId) {
    Optional<FileToUpload> fileOptional = fileDBRepository.getFileByContrat(contratId);
    if (fileOptional.isPresent()) {
      return fileOptional.get().getData();
    }
    return null;
  }

  public Stream<FileToUpload> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
}
