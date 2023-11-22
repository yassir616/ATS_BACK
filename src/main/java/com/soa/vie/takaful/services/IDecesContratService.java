package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.DecesContrat;
import com.soa.vie.takaful.requestmodels.CreateUpdateDecesContrat;
import com.soa.vie.takaful.requestmodels.UpdateDecesContrat;
import com.soa.vie.takaful.requestmodels.UpdateDecesContratStatusModel;
import com.soa.vie.takaful.requestmodels.updateDecesContratDateEffetModel;
import com.soa.vie.takaful.responsemodels.ContractResponseModel;
import com.soa.vie.takaful.responsemodels.DecesContratResponseModel;
import com.soa.vie.takaful.responsemodels.updateDecesContratDateEffetResponse;
import com.soa.vie.takaful.util.ContratStatus;

import org.springframework.data.domain.Page;

public interface IDecesContratService {

        public DecesContratResponseModel createDecesContrat(CreateUpdateDecesContrat model)
                        throws InterruptedException, ExecutionException;

        public DecesContratResponseModel findContratById(String id) throws InterruptedException, ExecutionException;

        public DecesContratResponseModel updateDecesContrat(String id, UpdateDecesContrat model)
                        throws InterruptedException, ExecutionException;

        public DecesContratResponseModel updateDecesContratStatus(String id, UpdateDecesContratStatusModel model)
                        throws InterruptedException, ExecutionException;
        
        public updateDecesContratDateEffetResponse updateContratDateEffet(String id, updateDecesContratDateEffetModel model)
                        throws InterruptedException, ExecutionException;

        public Page<DecesContratResponseModel> getDecesContrats(int page, int limit, String sort, String direction)
                        throws InterruptedException, ExecutionException;

        public DecesContratResponseModel getDecesContratById(String id) throws InterruptedException, ExecutionException;

        public Page<ContractResponseModel> searchContratParPartenaire(int page, int limit, String searchBy,
                        String searchFor, String partnerId) throws InterruptedException, ExecutionException;

        public Page<ContractResponseModel> searchContrat(int page, int limit, String searchBy, String searchFor)
                        throws InterruptedException, ExecutionException;

        public Page<DecesContratResponseModel> getDecesContartsByStatus(ContratStatus status, int page, int limit,
                        String sort, String direction) throws InterruptedException, ExecutionException;

        public Page<DecesContratResponseModel> getContratByPartenaire(int page, int limit,
                        String sort, String direction, String PartenaireId)
                        throws InterruptedException, ExecutionException;;

        public DecesContratResponseModel updateDecesContratOrientation(String id, String orientation)
                        throws InterruptedException, ExecutionException;

        public DecesContrat findOrInsert(String id);

}
