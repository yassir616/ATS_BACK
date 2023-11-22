package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.entitymodels.ProduitMrb;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PeriodiciteResponseModel extends AbstractBaseEntity {

    private String abb;

    private String libelle;
    
    @JsonIgnore
    private List<Produit> produits;

    @JsonIgnore
    private List<ProduitMrb> produitsMrb;

}