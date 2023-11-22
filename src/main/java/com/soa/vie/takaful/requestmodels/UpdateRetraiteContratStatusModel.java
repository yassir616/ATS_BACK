package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRetraiteContratStatusModel {
    private String status;
    private String typeAvenantId;
}
