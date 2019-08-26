package com.StartNotes.dto;

import lombok.Data;

@Data
public class Maildto 
{
	private String emailId;

	public String getEmailId() 
	{
		return emailId;
	}
}
