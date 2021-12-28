package com.jwt.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.entity.User;
import com.jwt.exception.ErrorMessage;
import com.jwt.exception.UserException;
import com.jwt.model.UserModel;
import com.jwt.request.UserRequest;
import com.jwt.response.UserResponse;
import com.jwt.service.IUserService;
import com.jwt.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IUserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Operation(summary = "Get a User by username")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the user", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserModel.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> getUser(@RequestParam String username) {
		if (!userService.userExistsByUsername(username)) {
			log.info("User not found with username {}", username);
			throw new UserException("User not found with username : " + username + "!!!");
		}
		UserModel returnUserModel = mapToModel(userService.getUserByUsername(username));
		return ResponseEntity.ok(returnUserModel);
	}

	@Operation(summary = "Get a User for login")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the user", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserModel.class)) }) })
	@GetMapping(value = "/one", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> getOneUser() {
		List<User> allUsers = userService.getAllUsers();
		if (!(allUsers.size() > 0)) {
			throw new UserException("No Users Found");
		}
		UserModel returnUserModel = mapToModel(allUsers.get(0));
		return ResponseEntity.ok(returnUserModel);
	}

	@Operation(summary = "Get all Users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found all users", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserModel.class))) }),
			@ApiResponse(responseCode = "404", description = "Users not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserModel>> getAllUsers() {
		List<User> allUsers = userService.getAllUsers();
		if (!(allUsers.size() > 0)) {
			throw new UserException("No Users Found");
		}
		List<UserModel> listOfUserModels = allUsers.stream()
				.collect(Collectors.mapping(user -> mapToModel(user), Collectors.toList()));
		return ResponseEntity.ok(listOfUserModels);
	}

	@Operation(summary = "Create User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully Created a User", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserModel.class)) }),
			@ApiResponse(responseCode = "400", description = "Cannot Create a User as Username is already taken !!!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> saveUser(@Valid @RequestBody UserModel userModel) {
		if (userService.userExistsByUsername(userModel.getUsername())) {
			log.info("Username : {} already taken", userModel.getUsername());
			throw new UserException("Username already taken !!!");
		}
		log.info("user : {}", userModel);
		User user = userService.saveUser(mapToEntity(userModel));
		log.info("User {} has been saved", userModel.toString());

		UserModel returnUserModel = mapToModel(user);
		return ResponseEntity.ok(returnUserModel);
	}

	@Operation(summary = "Login")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully Logged in", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid Credentails", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody UserRequest userRequest) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
		// String token =
		// jwtUtil.generateToken(userService.getUserByUsername(userRequest.getUsername()));
		String token = jwtUtil.generateToken(userRequest.getUsername());
		return ResponseEntity.ok(new UserResponse(token, "Sucessfully Generated JWT Token"));
	}

	private User mapToEntity(UserModel userModel) {
		try {
			return modelMapper.map(userModel, User.class);
		} catch (Exception e) {
			log.error("Not able to map UserModel :' {} ' to UserEntity", userModel);
			throw new RuntimeException("Not able to UserModel to UserEntity");
		}
	}

	private UserModel mapToModel(User user) {
		try {
			return modelMapper.map(user, UserModel.class);
		} catch (Exception e) {
			log.error("Not able to map UserEntity :' {} ' to UserModel", user);
			throw new RuntimeException("Not able to map UserEntity to UserModel");
		}
	}
}
