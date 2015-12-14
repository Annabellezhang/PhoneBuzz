package com.coding.simpleTwiMLPhoneBuzz;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

/**
 * @author Chenyun Zhang
 * This is the main part of fizzbuzz game.
 */
@SuppressWarnings("serial")
public class FizzBuzzServlet extends HttpServlet {
	private static final int FIZZ_NUM = 3;
	private static final int BUZZ_NUM = 5;
	private static final String FIZZ = "fizz";
	private static final String BUZZ = "buzz";
	
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the digits user pressed
    	String digits = request.getParameter("Digits");
        TwiMLResponse twiml = new TwiMLResponse();
        // do fizzbuzz game
        if (digits != null) {
            int digit = Integer.parseInt(digits);
            String res = "";
            if (digit % FIZZ_NUM == 0) {
            	res += FIZZ;
            }
            if (digit % BUZZ_NUM == 0) {
            	res += BUZZ;
            }
            if (res.equals("")) {
            	res += digits;
            }
            // Response
            Say say = new Say("Your number in fizzbuzz game is " + res);
            try { 
                twiml.append(say);
            } catch (TwiMLException e) {
                e.printStackTrace();
            }
        } else {
        	// if digit is not valid, start again
            response.sendRedirect("/phonebuzz");
            return;
        }
        response.setContentType("application/xml");
        response.getWriter().print(twiml.toXML());
    }
}
