package com.StartNotes.utility;


import org.springframework.stereotype.Component;

@Component
public class Utility
{
    
	public static String getUrl(Long id) 
	{
		TokenGeneration tokenUtil = new TokenGeneration();
		return "http://localhost:8080/user/" + tokenUtil.createToken(id);
	}

}