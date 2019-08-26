package com.StartNotes.service;

import java.io.UnsupportedEncodingException;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.StartNotes.dto.Logindto;
import com.StartNotes.dto.Userdto;
import com.StartNotes.exception.Exception;
import com.StartNotes.model.Note;
import com.StartNotes.model.User;
import com.StartNotes.response.Response;
import com.StartNotes.response.ResponseToken;
@Service
public interface UserService 
{
	//register
	Response Register(Userdto userDto) throws Exception, UnsupportedEncodingException;

	//Login
	ResponseToken Login(Logindto loginDto) throws Exception, UnsupportedEncodingException;

	//verification of email
	 Response validateEmailId(String token) throws Exception ;
	
	//Authenticate user
	ResponseToken authentication(Optional<User> user, String password, String email) 
			throws UnsupportedEncodingException, Exception;
}
