package com.soa.vie.takaful.responsemodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import com.soa.vie.takaful.entitymodels.DecesProduitCauseRestitution;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.entitymodels.TypePrestationProduit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PieceJointeResponseModel extends AbstractBaseEntity {

    /**
     *
     */
    private String code;

    private String libelle;

    private String necessity;

    @JsonIgnore
    private List<TakafulUser> users;

    @JsonIgnore
    private List<DecesProduitCauseRestitution> decesProduitCauseRestitutions;

    @JsonIgnore
    private List<TypePrestationProduit> typePrestationProduit;

}