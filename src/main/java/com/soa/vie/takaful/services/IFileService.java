package com.soa.vie.takaful.services;

import java.io.IOException;
import java.util.stream.Stream;

import com.soa.vie.takaful.entitymodels.FileToUpload;
import com.soa.vie.takaful.requestmodels.FileRequestModel;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    public FileToUpload store(MultipartFile file, FileRequestModel fileRequest) throws IOException;

    public FileToUpload getFile(String id);

    public Stream<FileToUpload> getAllFiles();

}