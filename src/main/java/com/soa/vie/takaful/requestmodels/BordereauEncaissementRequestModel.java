package com.soa.vie.takaful.requestmodels;


import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class BordereauEncaissementRequestModel {
    private String refBordereau;
    private String compteBancaire;
    private String pointVente;
    private String montantTotal;
    private Date creationDate;
    private String id;
    private List<BordereauxEncaissementTableRequestModel> tableEncaissements;

}
