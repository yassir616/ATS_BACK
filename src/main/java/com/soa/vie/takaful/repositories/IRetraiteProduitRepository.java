package com.soa.vie.takaful.repositories;
import com.soa.vie.takaful.entitymodels.RetraiteProduit;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface IRetraiteProduitRepository extends PagingAndSortingRepository<RetraiteProduit,String> {
    public RetraiteProduit findByCode(String code);

    public Optional<RetraiteProduit> findById(String id);
}
