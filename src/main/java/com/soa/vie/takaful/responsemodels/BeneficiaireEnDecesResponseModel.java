package com.soa.vie.takaful.responsemodels;

import com.soa.vie.takaful.entitymodels.AbstractBaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaireEnDecesResponseModel extends AbstractBaseEntity {
    private String nom;
    private String prenom;
    private String rib;
    private String cin;
}
