package com.soa.vie.takaful.repositories;

import java.util.List;

import com.soa.vie.takaful.entitymodels.FileToUploadSinistre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileSinistreRepository extends JpaRepository<FileToUploadSinistre, String> {

    @Query(value="SELECT * FROM file_to_upload_sinistre f WHERE f.prestation_id = ? AND f.is_received = 'true'", nativeQuery = true)
    public List<FileToUploadSinistre> findAllByPrestationId(String prestationId);


}
