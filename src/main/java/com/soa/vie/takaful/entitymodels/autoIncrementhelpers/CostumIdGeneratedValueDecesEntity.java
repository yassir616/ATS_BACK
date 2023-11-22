package com.soa.vie.takaful.entitymodels.autoIncrementhelpers;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CostumIdGeneratedValueDecesEntity implements Serializable {

    private static final String AUTO_GENERATION_SEQUENCE_PRODUIT = "autogeneration_sequence_produit";
    private static final String ID_GENERATOR_PATH = "com.soa.vie.takaful.util.sequencegenerators.DatePrefixedNumeroPolice";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = AUTO_GENERATION_SEQUENCE_PRODUIT)
    @GenericGenerator(name = AUTO_GENERATION_SEQUENCE_PRODUIT, strategy = ID_GENERATOR_PATH)
    private String id;

}
