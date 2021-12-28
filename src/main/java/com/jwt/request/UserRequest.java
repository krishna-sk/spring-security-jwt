package com.jwt.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

	@Size(min = 8, max = 20, message = "password Must be between 8 and 20 Characters")
	@NotNull(message = "password cannot be null ")
	@NotBlank(message = "password cannot be empty")
	private String password;

}
