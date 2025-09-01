package com.vaultx.otp;

import com.twilio.Twilio;

public class TwilioConfig
{
    // Twilio Account SID - unique identifier for the Twilio account
    public static final String ACCOUNT_SID = "ACd1760497401ad99109b208a94f05c763";//currently I had replaced it with actual id

    // Twilio Auth Token - used for authenticating API requests,replaced it wuth actaul id
    public static final String AUTH_TOKEN = "611b3defd5ea61eb90071da48c666f87";


    // Static block to initialize Twilio client once when this class is loaded
    static
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
}
