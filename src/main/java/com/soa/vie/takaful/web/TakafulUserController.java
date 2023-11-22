package com.soa.vie.takaful.web;

import com.soa.vie.takaful.entitymodels.TakafulUser;
import com.soa.vie.takaful.repositories.ITakafulUserRepository;
import com.soa.vie.takaful.requestmodels.SingingUpUserRequestModel;
import com.soa.vie.takaful.requestmodels.UserUpdateRequestModel;
import com.soa.vie.takaful.responsemodels.TakafulUserResponseModel;
import com.soa.vie.takaful.services.IUserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/private")
public class TakafulUserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    private ITakafulUserRepository userRepository;

    @PostMapping("sign-up")
    public TakafulUserResponseModel registerUser(@RequestBody SingingUpUserRequestModel userModel) throws Exception {

        return modelMapper.map(this.userService.createUser(userModel), TakafulUserResponseModel.class);
    }

    @GetMapping(path = "user/{id}")
    public TakafulUserResponseModel getUser(@PathVariable String id) {
        return modelMapper.map(userService.getUserById(id), TakafulUserResponseModel.class);
    }

    @PutMapping(path = "user/{id}")
    public TakafulUserResponseModel updateUser(@PathVariable String id, @RequestBody UserUpdateRequestModel userModel)
            throws InterruptedException, ExecutionException {
        return modelMapper.map(userService.updateUser(id, userModel), TakafulUserResponseModel.class);
    }

    @GetMapping(path = "user")
    public Page<TakafulUserResponseModel> getAllUsers(@RequestParam("page") int page,
            @RequestParam(name = "limit", defaultValue = Integer.MAX_VALUE + "") int limit,
            @RequestParam("sort") String sort, @RequestParam("direction") String direction) {
        List<TakafulUserResponseModel> list = userService.getUsers(page, limit, sort, direction)
                .stream()
                .map(o -> modelMapper.map(o, TakafulUserResponseModel.class))
                .collect(Collectors.toList());

        return new PageImpl<>(list);
    }

    @DeleteMapping("user/delete/{id}")
    public void deleteUser(@PathVariable String id) {

        userService.deleteUser(id);
    }

    @PatchMapping("user/{userId}/{password}")
    public ResponseEntity<TakafulUser> updateEmployeePartially(@PathVariable String userId,
            @PathVariable String password) {
        try {
            Optional<TakafulUser> userOptional = userRepository.findById(userId);
            TakafulUser user = userOptional.get();
            user.setEncryptePassword(bcryptPasswordEncoder.encode(password));
            System.out.println("changement de password");
            return new ResponseEntity<TakafulUser>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
