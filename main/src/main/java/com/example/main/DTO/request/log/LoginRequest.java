package com.example.main.DTO.request.log;


import com.example.main.entity.log.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private ArrayList<Role> roles;

    public ArrayList<Role> getRoles() {
        return roles;
    }
}