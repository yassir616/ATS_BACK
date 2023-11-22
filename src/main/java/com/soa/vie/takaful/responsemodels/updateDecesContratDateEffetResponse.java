package com.soa.vie.takaful.responsemodels;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class updateDecesContratDateEffetResponse {
    private String id;
    private Date dateEffet;
    private String status;
    private int flag;
}