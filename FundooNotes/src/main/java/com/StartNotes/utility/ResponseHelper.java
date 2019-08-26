package com.StartNotes.utility;

import com.StartNotes.response.Response;

public class ResponseHelper
{
	public static Response statusResponse(int code, String message) 
	{
		Response statusResponse = new Response();
		statusResponse.setStatusMessage(message);
		statusResponse.setStatusCode(code);
		return statusResponse;
	}
}
