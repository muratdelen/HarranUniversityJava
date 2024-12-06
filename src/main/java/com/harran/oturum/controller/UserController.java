package com.harran.oturum.controller;

import com.harran.oturum.model.User;
import com.harran.oturum.service.JwtService;
import com.harran.oturum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("register")
	public User register(@RequestBody User user) {
		return userService.saveUser(user);
	}

	@GetMapping("users")
	public Iterable<User> getAllUsers(){
		return userService.getAllUsers();
	}
	@GetMapping("user/{username}")
	public User getUserByUsername(@PathVariable("username") String username){
		return userService.getUser(username);
	}

	@PutMapping("user/{username}")
	public User updateUser(@PathVariable("username") String username, @RequestBody User user){
		return userService.updateUser(username,user);
	}

	@PostMapping("login")
	public String login(@RequestBody User user){

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        boolean is_authenticated = authentication.isAuthenticated();
		if(is_authenticated)
			return jwtService.generateToken(user.getUsername());
		else
			return "Login Failed";

	}

}
