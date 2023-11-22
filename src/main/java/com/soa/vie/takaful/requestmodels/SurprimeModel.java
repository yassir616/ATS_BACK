package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurprimeModel {

    private float montantSurprime;
    private float surprimeHT;
    private float surprimeTaxe;
    private float surprimeTTC;
    private float tauxSurprime;

}
