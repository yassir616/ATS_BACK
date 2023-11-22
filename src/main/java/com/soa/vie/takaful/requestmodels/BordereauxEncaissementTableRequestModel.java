package com.soa.vie.takaful.requestmodels;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BordereauxEncaissementTableRequestModel {
    private String dateEncaissement;
    private String numeroContrat;
    private String numeroQuittance;
    private String numeroEncaissement;
    private String refEncaissement;
    private String montantEncaissement;
    private String modeEncaissement;
}
