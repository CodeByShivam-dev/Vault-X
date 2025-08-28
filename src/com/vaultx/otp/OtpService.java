package com.vaultx.otp;
import com.vaultx.otp.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;
public class OtpService
{
    //  Thsee are Twilio account credentials fetched from a separate  Twilo config class for security and reusability
    public static final String ACCOUNT_SID = TwilioConfig.ACCOUNT_SID;
    public static final String AUTH_TOKEN = TwilioConfig.AUTH_TOKEN;

    /**
     * Sends a 6-digit OTP to the specified WhatsApp number using Twilio's API
     *  toWhatsAppNumber - recipient's WhatsApp phone number should be in  (in international format)
     */
    public void sendOtp(String toWhatsAppNumber)
    {
        // Initialize Twilio client with account credentials
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Generate a random 6-digit OTP (100000 to 999999)
        int otp = 100000 + new Random().nextInt(900000);

        // send a message to the user device including the OTP
        String messageBody = "Your VaultX verification code is: " + otp;

        // Create and send WhatsApp message via Twilio API
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:" + toWhatsAppNumber),
                        new PhoneNumber("whatsapp:+14155238886"),
                        messageBody)
                .create();

        // Log the message SID to confirm that OTP was sent successfully
        System.out.println("OTP sent with SID: " + message.getSid());
    }
}
