package com.StartNotes.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

import javax.tools.JavaFileManager.Location;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.StartNotes.dto.Logindto;
import com.StartNotes.dto.Maildto;
import com.StartNotes.dto.Userdto;
import com.StartNotes.exception.Exception;
import com.StartNotes.model.Note;
import com.StartNotes.model.User;
import com.StartNotes.repository.NoteRepository;
import com.StartNotes.repository.UserRepo;
import com.StartNotes.response.Response;
import com.StartNotes.response.ResponseToken;
import com.StartNotes.utility.ResponseHelper;
import com.StartNotes.utility.TokenGeneration;
import com.StartNotes.utility.Utility;

@Component
@SuppressWarnings("unused")
@PropertySource("classpath:message.properties")
@Service("userService")
public  class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenGeneration tokenUtil;

	@Autowired
	private Response statusResponse;
	
	@Autowired
	Utility utility;

	@Autowired
	private Environment environment;
	
	public Response Register(Userdto userDto) {

		String emailid = userDto.getEmailId();	
		User user = modelMapper.map(userDto, User.class);
		Optional<User> alreadyPresent = userRepo.findByEmailId(user.getEmailId());
		if (alreadyPresent.isPresent()) {
			throw new Exception(environment.getProperty("status.register.emailExistError"));
		}
	
		String password = passwordEncoder.encode(userDto.getPassword());

		user.setPassword(password);
		user = userRepo.save(user);
		Long userId = user.getuserId();
		System.out.println(emailid + " " + userId);
		statusResponse = ResponseHelper.statusResponse(200, "register successfully");
		return statusResponse;
	}
	
	public ResponseToken Login(Logindto loginDto) {
		
		Optional<User> user = userRepo.findByEmailId(loginDto.getEmailId());
		System.out.println(user);
		ResponseToken response = new ResponseToken();
		if (user.isPresent()) {
			System.out.println("password..." + (loginDto.getPassword()));
			
		return authentication(user, loginDto.getPassword(),loginDto.getEmailId());

		}
		
		return response;

	}
		
	public ResponseToken authentication(Optional<User> user, String password,String email) {

		ResponseToken response = new ResponseToken();
		
		String token1 = tokenUtil.createToken(user.get().getuserId());
		validateEmailId(token1);
		
		if (user.get().isVerify()) {
			boolean status = passwordEncoder.matches(password, user.get().getPassword());
			System.out.println("status"+status);
			if (status == true) {
				System.out.println("logged in");
				String token = tokenUtil.createToken(user.get().getuserId());
	
				System.out.println(token);
				response.setToken(token);
				response.setStatusCode(200);
				response.setStatusMessage(environment.getProperty("user.login"));
				return response;
			}
			throw new Exception(401, environment.getProperty("user.login.password"));
		}
		throw new Exception(401, environment.getProperty("user.login.verification"));
	}

	public Response validateEmailId(String token) {
		Long id = tokenUtil.decodeToken(token);
		User user = userRepo.findById(id)
							.orElseThrow(() -> new Exception
									(404, environment.getProperty("user.validation.email")));
		user.setVerify(true);
		userRepo.save(user);
		statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("user.validation"));
		return statusResponse;
	} 
	
}