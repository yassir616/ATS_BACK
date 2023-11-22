package com.soa.vie.takaful.services;

import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.entitymodels.Avenant;
import com.soa.vie.takaful.entitymodels.DecesContrat;
import com.soa.vie.takaful.entitymodels.DecesContratHistorique;
import com.soa.vie.takaful.requestmodels.UpdateDecesContrat;

public interface IDecesContartHistoriqueService {

    public DecesContratHistorique createDecesContratHistorique(DecesContrat decesContrat, UpdateDecesContrat model,
            Avenant avenant) throws InterruptedException, ExecutionException, ExecutionException;
}
