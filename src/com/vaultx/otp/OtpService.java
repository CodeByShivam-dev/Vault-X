package com.vaultx.otp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;

public class OtpService {

    // Twilio Account credentials from config
    public static final String ACCOUNT_SID = TwilioConfig.ACCOUNT_SID;
    public static final String AUTH_TOKEN = TwilioConfig.AUTH_TOKEN;

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    /**
     * Sends a 6-digit OTP to the specified WhatsApp number using Twilio API
     * @param toWhatsAppNumber Recipient WhatsApp number in international format without "whatsapp:" prefix (e.g., "919876543210")
     * @return The generated OTP String for server-side verification
     */
    public String sendOtp(String toWhatsAppNumber) {
        // Generate random 6-digit OTP from 000000 to 999999
        String otp = String.format("%06d", new Random().nextInt(1000000));

        String messageBody = "Your VaultX verification code is: " + otp;

        // Send WhatsApp message via Twilio API
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:" + toWhatsAppNumber),
                        new PhoneNumber("whatsapp:+14155238886"), // Twilio Sandbox WhatsApp number
                        messageBody)
                .create();

        System.out.println("OTP sent with SID: " + message.getSid());

        // Return OTP so caller can store and verify later
        return otp;
    }
}
