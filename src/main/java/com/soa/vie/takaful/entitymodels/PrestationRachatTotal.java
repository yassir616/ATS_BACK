package com.soa.vie.takaful.entitymodels;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "prestationId")
public class PrestationRachatTotal extends Prestation {


    private static final long serialVersionUID = 1L;

    @Column(columnDefinition = "datetime")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm")
    private Date dateDepart;

    @Column
    float montantBrutRachatTotal;

    @Column
    float montantNetRachatTotal;

    @Column
    float ir ;



}
