package com.coding.dialingPhoneBuzz;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;

/**
 * @author Chenyun Zhang
 * This is the phase 2 of PhoneBuzz.
 */
@SuppressWarnings("serial")
public class PhoneDialServlet extends HttpServlet {
	//Enter your Twilio sid and token here
	public static final String ACCOUNT_SID = "AC65b824913b2d3fec29b9a7e2949733e4";
	public static final String AUTH_TOKEN = "212b9672ba78a9181e8275fea0251629";
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Get phone# from html input
		String phonenumber = request.getParameter("phonenumber");
		
		// Create client
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account mainAccount = client.getAccount();
        CallFactory callFactory = mainAccount.getCallFactory();
        Map<String, String> callParams = new HashMap<String, String>();
        
        //Set call information
        callParams.put("To", phonenumber); 
        callParams.put("From", "(201)591-3079"); // Replace with your Twilio number
        callParams.put("Url", "http://1-dot-phonebuzz-1158.appspot.com/phonebuzz");
        
        // Make the call
        Call call;
		try {
			call = callFactory.create(callParams);
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
