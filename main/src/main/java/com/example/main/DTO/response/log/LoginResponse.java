package com.example.main.DTO.response.log;

import com.example.main.entity.log.Login;
import com.example.main.entity.log.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;


@Setter
@Getter
public class LoginResponse {

    @NotNull
    private Long loginId;
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private boolean enabled;

    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public LoginResponse(Login login) {
        if(login==null)
            return;
        loginId = login.getLoginId();
        username = login.getUsername();
        email = login.getEmail();
        enabled = login.isEnabled();
    }
}
