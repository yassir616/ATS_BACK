package com.soa.vie.takaful.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleModelRequest {

    private String name;

    private String value;

    public SimpleModelRequest(String name,String value){

        this.name = name;
        this.value = value;
    }
}
