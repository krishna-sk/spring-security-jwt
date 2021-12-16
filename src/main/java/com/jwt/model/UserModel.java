package com.jwt.model;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jwt.enums.PERMISSIONS;
import com.jwt.enums.ROLES;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @NotNull(message = "Name cannot be null ")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "username cannot be null or empty")
    private String username;

    @NotNull(message = "password cannot be null ")
    @NotBlank(message = "password cannot be empty")
    @Size(min = 8, max = 20, message = "password must be between 8 and 20 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotEmpty(message = "Roles cannot be null or empty")
    private Set<ROLES> roles;

    @NotEmpty(message = "Permissions cannot be null or empty")
    private Set<PERMISSIONS> permissions;
}
