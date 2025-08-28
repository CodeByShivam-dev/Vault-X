package com.vaultx.otp;
import com.vaultx.otp.TwilioConfig;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;
public class OtpService
{

    public static final String ACCOUNT_SID = TwilioConfig.ACCOUNT_SID;
    public static final String AUTH_TOKEN = TwilioConfig.AUTH_TOKEN;

    public void sendOtp(String toWhatsAppNumber)
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        int otp = 100000 + new Random().nextInt(900000);
        String messageBody = "Your VaultX verification code is: " + otp;

        Message message = Message.creator(
                        new PhoneNumber("whatsapp:" + toWhatsAppNumber),
                        new PhoneNumber("whatsapp:+14155238886"),
                        messageBody)
                .create();

        System.out.println("OTP sent with SID: " + message.getSid());
    }
}
