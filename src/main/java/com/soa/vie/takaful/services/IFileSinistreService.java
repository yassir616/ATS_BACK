package com.soa.vie.takaful.services;

import java.io.IOException;

import com.soa.vie.takaful.entitymodels.FileToUploadSinistre;
import com.soa.vie.takaful.requestmodels.FileSinistreRequestModel;

import org.springframework.web.multipart.MultipartFile;

public interface IFileSinistreService {

    public FileToUploadSinistre store(MultipartFile file, FileSinistreRequestModel fileRequest) throws IOException;

    public void deleteFile(String id);


}