package com.soa.vie.takaful.services;

import com.soa.vie.takaful.entitymodels.TakafulUser;

import com.soa.vie.takaful.requestmodels.SingingUpUserRequestModel;
import com.soa.vie.takaful.requestmodels.UserUpdateRequestModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.soa.vie.takaful.responsemodels.TakafulUserResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

	public TakafulUser createUser(SingingUpUserRequestModel user) throws InterruptedException, ExecutionException;

	public TakafulUser getUser(String email);

	public TakafulUser getUserById(String userId);

	public TakafulUserResponseModel updateUser(String userId, UserUpdateRequestModel user)
			throws InterruptedException, ExecutionException;

	public Page<TakafulUser> getUsers(int page, int limit, String sort, String direction);

	public List<TakafulUser> searchByName(String name);

	public List<TakafulUser> searchByEmail(String email);

	public void deleteUser(String id);

	public List<TakafulUser> getUsersByPointVente(String PointVenteId);

	public List<TakafulUser> getUserHavingPriSvilege(String privilege);

}
