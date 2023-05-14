package com.example.main.DTO.request;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ImageRequest {


    @NotNull
    private Long imageId;

    @NotNull
    private String data;

    @NotNull
    private String name;

    @NotNull
    private String format;

}
