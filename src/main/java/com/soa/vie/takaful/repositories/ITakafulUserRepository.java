package com.soa.vie.takaful.repositories;

import java.util.List;
import java.util.Optional;

import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.TakafulUser;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface ITakafulUserRepository extends PagingAndSortingRepository<TakafulUser, String> {

	@Query(value = "select Distinct tu.* from takaful_user tu,role r,users_roles ur,roles_privileges rp,privilege p where rp.privilege_id=p.id and rp.role_id=r.id and ur.user_id=tu.id and ur.role_id=r.id and tu.id like ?% \n-- #pageable\n", countQuery = "select Distinct tu.* from takaful_user tu,role r,users_roles ur,roles_privileges rp,privilege p where rp.privilege_id=p.id and rp.role_id=r.id and ur.user_id=tu.id and ur.role_id=r.id and tu.id like ?%", nativeQuery = true)
	public Optional<TakafulUser> findUserById(String id);

	public Optional<TakafulUser> findUserByEmail(String email);

	public List<TakafulUser> findByLastNameStartingWith(String name);

	public List<TakafulUser> findByEmailStartingWith(String email);

	public List<TakafulUser> findByPointVentes(List<PointVente> pointVentes);

	@Query(value = "select * from takaful_user u inner join users_roles ur on u.id=ur.user_id inner join roles_privileges rp on ur.role_id = rp.role_id inner join privilege p on p.id=rp.privilege_id where p.name =?", nativeQuery = true)
	public List<TakafulUser> findByPrivilege(String PriviligeName);

}