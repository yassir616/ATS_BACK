package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.ContratMrb;
import com.soa.vie.takaful.entitymodels.Produit;
import com.soa.vie.takaful.entitymodels.ProduitMrb;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExclusionResponseModel extends AbstractBaseEntity {

    private String exclusionNom;
    
    private String famille;
    
    @JsonIgnore
    private List<Produit> produits;
    
    @JsonIgnore
    private List<ContratMrb> contrats;
    
    @JsonIgnore
    private List<ProduitMrb> produitMrb;

}
