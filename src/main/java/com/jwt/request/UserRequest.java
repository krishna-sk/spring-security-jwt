package com.jwt.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "username cannot be null ")
    @NotBlank(message = "username cannot be empty")
    private String username;

    private String password;
    
}
