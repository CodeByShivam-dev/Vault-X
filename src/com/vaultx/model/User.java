package com.vaultx.model;
import java.util.UUID;

/**
 * 🧑‍💻 User Entity for VailtX Banking System
 *
 * Why this class exists?
 * 👉 Every banking application needs a way to represent a user in the system.
 * 👉 Instead of just storing random values, we wrap user information in a
 *     dedicated object. This makes the code cleaner, secure, and future-proof.
 *
 * Key Design Choices:
 * 1. Unique ID (UUID) → avoids duplicate conflicts (real banks never rely only on name/email).
 * 2. Encapsulation → all fields are private; access is controlled via getters & setters.
 * 3. Extendable → tomorrow, if we need to add Aadhaar, address, or KYC status,
 *    we can simply add new fields without breaking old code.
 */
    public class User
    {
        // unique id for user
        private String userId;

        private final long accountNumber;
        // 👨 Basic personal details
        private String name;
        private String fatherName;
        private String motherName;

        // 📩 Contact & KYC information
        private String email;
        private String panCard;
        private String phone;

        /**
         * Constructor ensures user object is created with all mandatory details.
         */
        public User(String userId, long accountNumber, String name, String fatherName, String motherName, String email, String panCard, String phone) {
            this.userId = userId;
            this.accountNumber = accountNumber;
            this.name = name;
            this.fatherName = fatherName;
            this.motherName = motherName;
            this.email = email;
            this.panCard = panCard;
            this.phone = phone;

        }
        // ===========================
        // Getters → Safe access points
        // ===========================

        public String getPhone() {
            return phone;
        }

        // ===========================
        // Setters → Controlled updates
        // ===========================

        // ⚠️ In real banking apps, PAN should not be editable → here allowed for demo.
        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFatherName() {
            return fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }

        public String getMotherName() {
            return motherName;
        }

        public void setMotherName(String motherName) {
            this.motherName = motherName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPanCard() {
            return panCard;
        }

        public void setPanCard(String panCard) {
            this.panCard = panCard;
        }
        /**
         * Converts User object to a human-readable string.
         * Helpful for debugging & logging.
         */

        @Override
        public String toString() {
            return "User{" +
                    "userId='" + userId + '\'' +
                    "accountNumber=" + accountNumber +
                    ", name='" + name + '\'' +
                    ", fatherName='" + fatherName + '\'' +
                    ", motherName='" + motherName + '\'' +
                    ", email='" + email + '\'' +
                    ", panCard='" + panCard + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

