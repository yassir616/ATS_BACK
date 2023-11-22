package com.soa.vie.takaful.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSingInRequestModel {

    private String email;

    private String password;

}
