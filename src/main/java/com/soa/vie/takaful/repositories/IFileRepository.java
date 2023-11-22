package com.soa.vie.takaful.repositories;

import java.util.Optional;

import com.soa.vie.takaful.entitymodels.FileToUpload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepository extends JpaRepository<FileToUpload, String> {

    @Query(value = "select * from file_to_upload where contrat_id =?", nativeQuery = true)
    public Optional<FileToUpload> getFileByContrat(String contrat);
}
