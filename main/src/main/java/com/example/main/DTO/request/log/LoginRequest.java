package com.example.main.DTO.request.log;


import com.example.main.entity.log.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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

    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }
}