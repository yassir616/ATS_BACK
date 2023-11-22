package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.CompteBancaire;
import com.soa.vie.takaful.entitymodels.Cotisation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EncaissementResponseModel extends AbstractBaseEntity {

    /**
     * 
     */

    private String numEncaissement;

    private float montantEncaissement;

    private String numReference;

    private String modeEncaissement;

    private Date dateEncaissement;

    private Date dateEtablissement;

    private float montantTaxe;

    private float montantQuittance;

    private Float montantTaxeParafiscale;

    private Float accessoire;

    private Float montantEmission;

    private Cotisation cotisation;

    private CompteBancaire compteBancaire;

}
