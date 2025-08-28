package com.vaultx.otp;

import com.twilio.Twilio;

public class TwilioConfig {
    public static final String ACCOUNT_SID = "ACd1760497401ad99109b208a94f05c763" +
            "\n";
    public static final String AUTH_TOKEN = "58a0114a5a181540491900930de2ae20";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
}
