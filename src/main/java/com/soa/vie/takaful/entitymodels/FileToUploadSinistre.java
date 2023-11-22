package com.soa.vie.takaful.entitymodels;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import javax.persistence.JoinColumn;
import javax.persistence.Lob;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FileToUploadSinistre extends AbstractBaseEntity {


    @Column
    private String name;

    @Column
    private String extension;

    @Lob
    @Column()
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "piece_jointe_id")
    private PieceJointe pieceJointe;

    @ManyToOne
    @JoinColumn(name = "prestation_id")
    private Prestation prestation;

    @Column(name = "is_received")
    private boolean is_received;

    // @Column(columnDefinition = "datetime")
	// @Temporal(TemporalType.DATE)
	// @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    @Column
    @Temporal(TemporalType.DATE)
	private Date dateReception;


    public FileToUploadSinistre(String name, String extension, byte[] data, PieceJointe pieceJointe,Prestation prestation,boolean is_received,Date dateReception) {
        this.name = name;
        this.extension = extension;
        this.data = data;
        this.pieceJointe =pieceJointe;
        this.prestation = prestation;
        this.is_received=is_received;
        this.dateReception=dateReception;
    }
}