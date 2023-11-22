package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EncaissementGroupeRequestModel {

    private String numeroLot;
    private String refEncaissement;
    private String modeEncaissement;
    private String numeroCompte;
    private Date dateEncaissement;

}
