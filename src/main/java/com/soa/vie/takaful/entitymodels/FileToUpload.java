package com.soa.vie.takaful.entitymodels;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FileToUpload extends AbstractBaseEntity {

    @Column
    private String name;

    @Column
    private String extension;

    @Lob
    @Column()
    private byte[] data;

    @OneToOne
    @JoinColumn(unique = true)
    private Contract contrat;

    public FileToUpload(String name, String extension, byte[] data, Contract contrat) {
        this.name = name;
        this.extension = extension;
        this.data = data;
        this.contrat = contrat;
    }

}