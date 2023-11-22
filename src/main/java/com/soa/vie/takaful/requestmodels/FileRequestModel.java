package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequestModel {

    private String name;
    private byte[] data;
    private String contract_id;

}
