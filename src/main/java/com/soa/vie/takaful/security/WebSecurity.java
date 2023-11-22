package com.soa.vie.takaful.security;

import javax.annotation.Resource;

import com.soa.vie.takaful.services.IUserService;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity(debug = false)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private final IUserService userDetailsService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Resource(name = "corsConfigurationSource")
	CorsConfigurationSource corsConfig;

	public WebSecurity(IUserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(corsConfig);
		http.csrf().disable().authorizeRequests()
				.antMatchers(SecurityCanstants.AUTORIZED_URLS, "/v2/api-docs", "/configuration/**", "/swagger*/**",
						"/webjars/**")
				.permitAll()
				.antMatchers(SecurityCanstants.GET_CREATE_UPDATE_ROLE, SecurityCanstants.CREATE_PRIVILEGE)
				.hasAuthority(SecurityCanstants.ROLES_MANAGER)
				.antMatchers(SecurityCanstants.GET_UPDATE_USER, SecurityCanstants.DELETE_USER,
						SecurityCanstants.CREATE_USER)
				.hasAuthority(SecurityCanstants.USERS_MANAGER)
				.antMatchers(SecurityCanstants.CREATE_INTERMEDIAIRE,
						SecurityCanstants.CREATE_AND_LIST_CAUSE_RESTITUTION,
						SecurityCanstants.CREATE_AND_LIST_PRODUIT_DECES,
						SecurityCanstants.CREATE_AND_LIST_TYPE_PRESTATION, SecurityCanstants.CREATE_AUXILIAIRE,
						SecurityCanstants.CREATE_AVENANT, SecurityCanstants.CREATE_HONORAIRE,
						SecurityCanstants.CREATE_POINT_VENTE, SecurityCanstants.CREATE_PRIVILEGE,
						SecurityCanstants.CREATE_PRODUIT_MRB, SecurityCanstants.CREATE_RESTITUTION,
						SecurityCanstants.UPDATE_AUXILIAIRE, SecurityCanstants.UPDATE_CAUSE_RESTITUTION,
						SecurityCanstants.UPDATE_HONORAIRE, SecurityCanstants.UPDATE_INTERMEDIAIRE,
						SecurityCanstants.UPDATE_POINT_VENTE, SecurityCanstants.UPDATE_PRODUIT_DECES,
						SecurityCanstants.UPDATE_PRODUIT_MRB, SecurityCanstants.UPDATE_RESTITUTION,
						SecurityCanstants.UPDATE_TYPE_PRESTATION, SecurityCanstants.LIST_AUXILIAIRE,
						SecurityCanstants.LIST_AVENANT, SecurityCanstants.LIST_HONORAIRE,
						SecurityCanstants.LIST_INTERMEDIAIRE, SecurityCanstants.LIST_POINT_VENTE,
						SecurityCanstants.LIST_PRODUIT_MRB, SecurityCanstants.LIST_RESTITUTION,
						SecurityCanstants.CREATE_TYPE_AUXILIAIRE, SecurityCanstants.UPDATE_TYPE_AUXILIAIRE,
						SecurityCanstants.LIST_TYPE_AUXILIAIRE)
				.hasAuthority(SecurityCanstants.AGENT_BUREAU_DIRECT)
				.anyRequest().authenticated().and().addFilter(getAuthenticationFilter())
				.addFilter(new AuthorizationFilter(authenticationManager()));

	}

	public AuthenticationFilter getAuthenticationFilter() throws Exception {

		final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
		authenticationFilter.setFilterProcessesUrl("/api/public/login");

		return authenticationFilter;
	}

}
