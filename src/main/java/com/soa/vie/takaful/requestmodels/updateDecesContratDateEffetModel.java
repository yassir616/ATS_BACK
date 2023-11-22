package com.soa.vie.takaful.requestmodels;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class updateDecesContratDateEffetModel {
    private String id;
    private Date dateEffet;
    private String status;
    private int flag;
}
