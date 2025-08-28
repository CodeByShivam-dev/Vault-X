package com.vaultx.otp;

import java.util.Scanner;

public class OtpMainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your WhatsApp phone number (with country code, e.g. 919876543210): ");
        String userPhoneNumber = scanner.nextLine();

        OtpService otpService = new OtpService();
        otpService.sendOtp(userPhoneNumber);

        scanner.close();
    }
}
