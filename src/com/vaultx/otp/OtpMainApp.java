package com.vaultx.otp;

import java.util.Scanner;

public class OtpMainApp {

    public static void main(String[] args)
    {
        // Create Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter their WhatsApp phone number with country code
        System.out.print("Enter your WhatsApp phone number (with country code, e.g. 919876543210): ");
        String userPhoneNumber = scanner.nextLine();

        // Instantiate the OTP service to handle OTP generation and sending
        OtpService otpService = new OtpService();

        // Send OTP to the user-provided WhatsApp number
        otpService.sendOtp(userPhoneNumber);

        scanner.close();
    }
}
