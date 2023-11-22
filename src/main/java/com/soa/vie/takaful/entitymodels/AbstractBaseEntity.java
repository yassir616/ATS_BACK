package com.soa.vie.takaful.entitymodels;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * this class will be the mother class of all our Entity Models Objects all of
 * them will have this class attributes
 * 
 */
@MappedSuperclass
@Getter
@Setter
@JsonIgnoreProperties({ "ProduitMrb" })
public abstract class AbstractBaseEntity implements Serializable {

	/**
	 *
	 */

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column
	private String fluxId;

	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
	private Date creationDate = new Date();

	protected AbstractBaseEntity() {
		this.id = UUID.randomUUID().toString();
		this.fluxId = UUID.randomUUID().toString();
	}
}