package com.soa.vie.takaful.web;

import com.soa.vie.takaful.entitymodels.FileToUploadSinistre;
import com.soa.vie.takaful.requestmodels.FileSinistreRequestModel;
import com.soa.vie.takaful.responsemodels.ResponseMessage;
import com.soa.vie.takaful.services.Impl.FileSinistreStorageService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/private")
public class FileSinistreController {

    @Autowired
    private FileSinistreStorageService sinistreStorageService;

    @PostMapping(value = "/uploadFileSinistre")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
            FileSinistreRequestModel fileRequest) {
        String message = "";
        try {
            sinistreStorageService.store(file, fileRequest);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("uploadedFiles")
	public List<FileToUploadSinistre> getFilesSinistreByPrestationId(@RequestParam String prestationId) throws Exception {

		return sinistreStorageService.uploadedFilesByPrestationId(prestationId);

    }

    @DeleteMapping("uploadedFiles/delete/{id}")
    public void deleteFile(@PathVariable String id) {

        sinistreStorageService.deleteFile(id);
    }
    
}
