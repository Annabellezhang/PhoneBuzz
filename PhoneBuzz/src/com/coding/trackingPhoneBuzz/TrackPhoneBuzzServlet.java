package com.coding.trackingPhoneBuzz;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;

/**
 * @author Chenyun Zhang
 * This the phase 4 for PhoneBuzz.NOT COMPLETED.
 */
@SuppressWarnings("serial")
public class TrackPhoneBuzzServlet extends HttpServlet {
	
	//Enter your Twilio sid and token here
	public static final String ACCOUNT_SID = "AC65b824913b2d3fec29b9a7e2949733e4";
	public static final String AUTH_TOKEN = "212b9672ba78a9181e8275fea0251629";
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// Get the time
		Calendar now = Calendar.getInstance();
		
		// Get the phone # and delay time from html input
		String phonenumber = request.getParameter("phonenumber");
		int delayTime = Integer.parseInt(request.getParameter("delaytime"));
		
		// Create a client
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account mainAccount = client.getAccount();
        CallFactory callFactory = mainAccount.getCallFactory();
        Map<String, String> callParams = new HashMap<String, String>();
        
        // Set call information
        callParams.put("To", phonenumber); 
        callParams.put("From", "(201)591-3079"); // Replace with your Twilio number
        callParams.put("Url", "http://2-dot-phonebuzz-1158.appspot.com/phonebuzz");
        Call call = null;     
        
        try {
        	//Make the delay
			Thread.sleep(delayTime * 1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        // Get call time
        Calendar callTime = Calendar.getInstance();
		
        try {
			//Make the call
			call = callFactory.create(callParams);
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().println(add(phonenumber, delayTime, now, callTime));
	}
	
	/**
	 * add function is used to store entity in Google Cloud datastore
	 * @param phonenumber
	 * @param delayTime
	 * @param now
	 * @param callTime
	 * @return
	 */
	public String add(String phonenumber, int delayTime, Calendar now, Calendar callTime) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity phonebuzz = new Entity("PhoneBuzz");
		phonebuzz.setProperty("phone", phonenumber);
		phonebuzz.setProperty("delayTime", delayTime);
		phonebuzz.setProperty("madetime", now);
		phonebuzz.setProperty("calltime", callTime);
		phonebuzz.setProperty("digit", 0);
		datastore.put(phonebuzz);
		return "OK!";
	}
}
