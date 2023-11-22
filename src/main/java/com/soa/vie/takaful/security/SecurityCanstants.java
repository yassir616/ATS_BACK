package com.soa.vie.takaful.security;

import com.soa.vie.takaful.SpringApplicationContext;

public class SecurityCanstants {

	public static final long EXPIRATION_TIME = 604800000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTORIZED_URLS = "/api/public/**";

	// privileges canstants
	public static final String USERS_MANAGER = "GESTION_UTILISATEURS";
	public static final String ROLES_MANAGER = "GESTION_ROLE_ET_PERMISSION";
	public static final String GESTION_SINISTRE = "GESTION_SINISTRE";
	public static final String GESTION_ACCEPTATION = "GESTION_ACCEPTATION";
	public static final String CREATION_CONTART = "CREATION_CONTRAT";
	public static final String SIMULATION = "SIMULATION";
	public static final String CONTART_MRB = "CONTART_MRB";
	public static final String CONTART_DECES = "CONTART_DECES";
	public static final String CONTART_RETRAITE = "CONTART_RETRAITE";

	public static final String AGENT_BUREAU_DIRECT = "GESTION_PATAMETRAGE";

	public static final String PRTNER_AGENT = "AGENT_PARTENAIRE";

	// user managment
	public static final String CREATE_USER = "/api/private/sign-up";
	public static final String GET_UPDATE_USER = "/api/private/user";
	public static final String DELETE_USER = "/api/private/user/delete";

	// roles and privileges managment
	public static final String GET_CREATE_UPDATE_ROLE = "/api/private/role/*";
	public static final String CREATE_PRIVILEGE = "/api/private/privilege/*";

	// parametrage

	// intermediaire
	public static final String CREATE_INTERMEDIAIRE = "/api/private/partenaire/*";
	public static final String UPDATE_INTERMEDIAIRE = "/api/private/partenaire/{id}";
	public static final String LIST_INTERMEDIAIRE = "/api/private/partenaires/*";

	// decesproduit
	public static final String CREATE_AND_LIST_PRODUIT_DECES = "/api/private/decesproduit/*";
	public static final String UPDATE_PRODUIT_DECES = "/api/private/decesproduit/{id}";

	// auxiliaire
	public static final String CREATE_AUXILIAIRE = "/api/private/auxiliaire/add/*";
	public static final String UPDATE_AUXILIAIRE = "/api/private/auxiliaire/{id}";
	public static final String LIST_AUXILIAIRE = "/api/private/auxiliaire/*";

	// honoraire
	public static final String CREATE_HONORAIRE = "/api/private/honoraire/add";
	public static final String UPDATE_HONORAIRE = "/api/private/honoraire/{id}";
	public static final String LIST_HONORAIRE = "/api/private/honoraires";

	// produit mrb
	public static final String CREATE_PRODUIT_MRB = "/api/private/addProductMrb";
	public static final String UPDATE_PRODUIT_MRB = "/api/private/updateProductMrb/{id}";
	public static final String LIST_PRODUIT_MRB = "/api/private/getAllProductMrb/*";

	// restitution
	public static final String CREATE_RESTITUTION = "/api/private/restitution";
	public static final String UPDATE_RESTITUTION = "/api/private/restitution/{id}";
	public static final String LIST_RESTITUTION = "/api/private/restitutions/*";

	// cause restitution
	public static final String CREATE_AND_LIST_CAUSE_RESTITUTION = "/api/private/causerestitution";
	public static final String UPDATE_CAUSE_RESTITUTION = "/api/private/causerestitution/{id}";

	// type prestation
	public static final String CREATE_AND_LIST_TYPE_PRESTATION = "/api/private/typeprestation";
	public static final String UPDATE_TYPE_PRESTATION = "/api/private/typeprestation/{id}";

	// type avenant
	public static final String CREATE_AVENANT = "/api/private/TypeAvenant/add";
	public static final String LIST_AVENANT = "/api/private/TypeAvenant/*";

	// point de vente
	public static final String CREATE_POINT_VENTE = "/api/private/pointvente/*";
	public static final String UPDATE_POINT_VENTE = "/api/private/pointvente/{id}";
	public static final String LIST_POINT_VENTE = "/api/private/pointventes/*";

	// type auxiliaire
	public static final String CREATE_TYPE_AUXILIAIRE = "/api/private/Typeauxiliaire/add";
	public static final String UPDATE_TYPE_AUXILIAIRE = "/api/private/Typeauxiliaire/{id}";
	public static final String LIST_TYPE_AUXILIAIRE = "/api/private/Typeauxiliaire/*";

	public static final String ADMIN = "ADMIN";

	static AppProperties appPropreties = (AppProperties) SpringApplicationContext.getBean("AppProperties");

	public static String getSecretToken() {

		return appPropreties.getSecretToken();
	}

}
