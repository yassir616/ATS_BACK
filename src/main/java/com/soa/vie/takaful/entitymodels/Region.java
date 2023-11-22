package com.soa.vie.takaful.entitymodels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Region {
    
    @Id
    @GeneratedValue
 private int id;

 private String name;
  

}
