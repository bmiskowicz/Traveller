package com.example.main.DTO.request;

import com.example.main.entity.log.Login;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    @NotNull
    private Long profileId;
    @NotNull
    private Login login;
    private byte icon;
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;
}