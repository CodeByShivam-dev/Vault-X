package com.vaultx.otp;

import com.twilio.Twilio;

public class TwilioConfig
{
    // Apne Twilio Console se ye values copy karo aur yahan paste karo
    public static final String ACCOUNT_SID = "YOUR_ACCOUNT_SID";
    public static final String AUTH_TOKEN = "58a0114a5a181540491900930de2ae20";
    public static final String VERIFY_SERVICE_SID = "YOUR_VERIFY_SERVICE_SID";

    // Twilio initialization method
    public static void init()
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
}
