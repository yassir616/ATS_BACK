package com.soa.vie.takaful.servicesImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.soa.vie.takaful.entitymodels.PointVente;
import com.soa.vie.takaful.entitymodels.Privilege;
import com.soa.vie.takaful.entitymodels.Role;
import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.repositories.IPointVenteRepository;
import com.soa.vie.takaful.repositories.IRoleRepository;
import com.soa.vie.takaful.repositories.ITakafulUserRepository;
import com.soa.vie.takaful.requestmodels.SingingUpUserRequestModel;
import com.soa.vie.takaful.requestmodels.UserUpdateRequestModel;
import com.soa.vie.takaful.responsemodels.TakafulUserResponseModel;
import com.soa.vie.takaful.services.IRoleService;
import com.soa.vie.takaful.services.IUserService;
import com.soa.vie.takaful.util.AccountStateEnum;
import com.soa.vie.takaful.util.Pagination;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private ITakafulUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IPointVenteRepository pointVenteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TakafulUser createUser(SingingUpUserRequestModel user) throws InterruptedException, ExecutionException {

        log.info("signing up new takaful user");

        TakafulUser registredUser = new TakafulUser();
        String useremail = user.getEmail();

        Optional<TakafulUser> userexists = userRepository.findUserByEmail(useremail);

        if (userexists.isPresent()) {
            log.info("takaful user already exist");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user already exists");
        }

        else {

            BeanUtils.copyProperties(user, registredUser);
            registredUser.setEncryptePassword(bcryptPasswordEncoder.encode(user.getPassword()));
            registredUser.setStatus(AccountStateEnum.ACTIVE);

            List<Role> roles = new ArrayList<>();
            addingRoles(user, roles);
            registredUser
                    .setRoles(roles.stream().map(o -> modelMapper.map(o, Role.class)).collect(Collectors.toList()));

            List<PointVente> pointVentes = new ArrayList<>();

            if (null != user.getPointVentes()) {
                for (PointVente pointVente : user.getPointVentes()) {

                    Optional<PointVente> pOptional = this.pointVenteRepository.findById(pointVente.getId());
                    if (pOptional.isPresent()) {
                        pointVentes.add(pOptional.get());
                    }
                }
            }
            registredUser.setPointVentes(pointVentes);

            registredUser = this.userRepository.save(registredUser);
        }
        return registredUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Optional<TakafulUser> userOptional = userRepository.findUserByEmail(email);
        TakafulUser user = null;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            List<Role> roles = new ArrayList<>();
            for (Role role : user.getRoles()) {
                Role dataRole = roleRepository.findRoleByName(role.getName());
                roles.add(dataRole);
            }
            return new User(user.getEmail(), user.getEncryptePassword(), getAuthorities(roles));
        } else
            throw new UsernameNotFoundException("No user with the given Email");
    }

    @Override
    public TakafulUser getUser(String email) {
        log.info("getting user with email : {}", email);
        Optional<TakafulUser> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else
            log.error("no user with the given email");
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No user with the given Email");
    }

    private List<? extends GrantedAuthority> getAuthorities(List<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    @Override
    public TakafulUser getUserById(String userId) {
        Optional<TakafulUser> userOptional = userRepository.findUserById(userId);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(userId);
        }
        TakafulUser user = userOptional.get();
        return user;
    }

    @Override
    public TakafulUserResponseModel updateUser(String userId, UserUpdateRequestModel user)
            throws InterruptedException, ExecutionException {

        log.info("updating takaful user id : {}", userId);

        Optional<TakafulUser> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            log.error("no user with the id : {}", userId);
            throw new UsernameNotFoundException(userId);
        }
        TakafulUser userEntity = userOptional.get();

        Optional<TakafulUser> userexists = userRepository.findUserByEmail(user.getEmail());

        if (userexists.isPresent() && !userEntity.getId().equals(userexists.get().getId())) {
            log.info("takaful user already exist");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "user already exists");
        }

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        if (null != user.getPassword()) {
            userEntity.setEncryptePassword(bcryptPasswordEncoder.encode(user.getPassword()));
        }
        userEntity.setEmail(user.getEmail());
        List<Role> roles = new ArrayList<>();
        if (null != user.getRoles()) {
            for (Role role : user.getRoles()) {
                roles.add(roleService.createRoleIfNotExist(role.getName(), role.getPrivileges()));
            }
        }
        userEntity.setRoles(roles.stream().map(o -> modelMapper.map(o, Role.class)).collect(Collectors.toList()));

        List<PointVente> pointVentes = new ArrayList<>();
        if (null != user.getPointVentes()) {
            for (PointVente pointVente : user.getPointVentes()) {

                Optional<PointVente> pOptional = this.pointVenteRepository.findById(pointVente.getId());
                if (pOptional.isPresent()) {
                    pointVentes.add(pOptional.get());
                }
            }
        }
        userEntity.setPointVentes(pointVentes);

        return modelMapper.map(userRepository.save(userEntity), TakafulUserResponseModel.class);

    }

    private List<String> getPrivileges(List<Role> roleList) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roleList) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> list) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : list) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public Page<TakafulUser> getUsers(int page, int limit, String sort, String direction) {

        return userRepository.findAll(Pagination.pageableRequest(page, limit, sort, direction));
    }

    @Override
    public List<TakafulUser> searchByName(String name) {

        return userRepository.findByLastNameStartingWith(name);
    }

    @Override
    public List<TakafulUser> searchByEmail(String email) {

        return userRepository.findByEmailStartingWith(email);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    protected void addingRoles(SingingUpUserRequestModel user, List<Role> roles)
            throws InterruptedException, ExecutionException {
        if (null != user.getRoles()) {
            for (Role role : user.getRoles()) {
                roles.add(roleService.createRoleIfNotExist(role.getName(), role.getPrivileges()));
            }
        }
    }

    @Override
    public List<TakafulUser> getUsersByPointVente(String pointVenteId) {
        Optional<PointVente> pointVente = this.pointVenteRepository.findById(pointVenteId);
        if (pointVente.isPresent()) {
            List<PointVente> pointDeVentes = new ArrayList<PointVente>();
            pointDeVentes.add(pointVente.get());
            return this.userRepository.findByPointVentes(pointDeVentes);
        }
        return null;
    }

    @Override
    public List<TakafulUser> getUserHavingPriSvilege(String privilege) {
        return this.userRepository.findByPrivilege(privilege);
    }
}
