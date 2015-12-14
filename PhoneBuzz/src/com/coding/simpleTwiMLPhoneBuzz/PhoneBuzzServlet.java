package com.coding.simpleTwiMLPhoneBuzz;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.*;

import com.twilio.sdk.verbs.Gather;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

/**
 * @author Chenyun Zhang
 * This is the phase 1 of PhoneBuzz.
 */
@SuppressWarnings("serial")
public class PhoneBuzzServlet extends HttpServlet {
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Create a TwiML response and add our friendly message.
        TwiMLResponse twiml = new TwiMLResponse();
        Say say = new Say("Please enter a number to play FizzBuzz and finished on a pound sign.");
        
        // Create a Gather to collect digit
        Gather gather = new Gather();
        
        // Set finish key
        gather.setFinishOnKey("#");
        
        // Set time out
        gather.setTimeout(10);
        
        // Set action, do fizzbuzz
        gather.setAction("/fizzbuzz");
        gather.setMethod("GET");
        
        // friendly message for timeout
        Say saygoodbye = new Say("We didn't receive any input, goodbye!");
        try {
        	gather.append(say);
            twiml.append(gather);
            twiml.append(saygoodbye);
        } catch (TwiMLException e) {
            e.printStackTrace();
        }
 
        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
    }
}
