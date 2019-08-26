package com.StartNotes.controller;

import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.StartNotes.dto.Logindto;
import com.StartNotes.dto.Userdto;
import com.StartNotes.exception.Exception;
import com.StartNotes.repository.UserRepo;
import com.StartNotes.response.Response;
import com.StartNotes.response.ResponseToken;
import com.StartNotes.service.UserService;

@CrossOrigin(allowedHeaders = "*", origins = "*")

@RestController
@RequestMapping("/user")
public class Controller 
{
	@Autowired
	UserService userService;

	@Autowired
	UserRepo userRepo;
	
	Response response;
	
	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody Userdto userDto)
			throws Exception, UnsupportedEncodingException 
	{
		Response response = userService.Register(userDto);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<ResponseToken> Login(@RequestBody Logindto logindto)
			throws Exception, UnsupportedEncodingException
	{
		ResponseToken response = userService.Login(logindto);
		System.out.println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{token}")
	public ResponseEntity<Response> emailValidation(@RequestHeader String token) throws Exception 
	{
		response = userService.validateEmailId(token);
			
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	 
}
