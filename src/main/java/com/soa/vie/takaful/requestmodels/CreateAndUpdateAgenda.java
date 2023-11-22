package com.soa.vie.takaful.requestmodels;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAndUpdateAgenda {

    private Date eventStartDate;

    private Date eventEndDate;

    private String eventDescription;

    private String eventColor;

    private String userId;

}