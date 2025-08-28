package com.vaultx.otp;

import com.twilio.Twilio;

public class TwilioConfig {
    public static final String ACCOUNT_SID = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
            "\n";
    public static final String AUTH_TOKEN = "Auth_token";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
}
