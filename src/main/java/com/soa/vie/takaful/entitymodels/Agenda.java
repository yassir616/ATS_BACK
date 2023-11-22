package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
public class Agenda extends AbstractBaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @Column(columnDefinition = "datetime",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventStartDate;

    @Column(columnDefinition = "datetime",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventEndDate;
    
    @Column(nullable = false)
    private String eventDescription;
    
    @Column(nullable = false)
    private String eventColor;

    @ManyToOne
    @JsonIgnore
    private TakafulUser user;
 


    


}

