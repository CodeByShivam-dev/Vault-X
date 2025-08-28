package com.vaultx.otp;

import com.twilio.Twilio;

public class TwilioConfig {
    public static final String ACCOUNT_SID = "zyz" +
            "\n";
    public static final String AUTH_TOKEN = "Auth_token_here";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
}
