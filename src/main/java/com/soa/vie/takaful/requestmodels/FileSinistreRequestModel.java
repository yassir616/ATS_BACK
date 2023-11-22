package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileSinistreRequestModel {

    private String name;
    private byte[] data;
    private String pieceJointeId;
    private String prestationId;
    
}
