package com.soa.vie.takaful.services;


import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.requestmodels.CreateAndUpdateRetraiteProduit;
import com.soa.vie.takaful.responsemodels.RetraiteProduitResponseModel;
import org.springframework.data.domain.Page;

public interface IRetraiteProduitService {


        public RetraiteProduitResponseModel createRetraiteProduit(CreateAndUpdateRetraiteProduit model) throws InterruptedException, ExecutionException;

        public RetraiteProduitResponseModel updateRetraiteProduit(String id, CreateAndUpdateRetraiteProduit model) throws InterruptedException, ExecutionException;

        public Page<RetraiteProduitResponseModel> getRetraiteProduits(int page, int limit, String sort, String direction) throws InterruptedException, ExecutionException;

        public RetraiteProduitResponseModel getRetraiteProduitById(String id) throws InterruptedException, ExecutionException ;

        public RetraiteProduitResponseModel getRetraiteProduitByCode(String code) throws InterruptedException, ExecutionException ;


}
