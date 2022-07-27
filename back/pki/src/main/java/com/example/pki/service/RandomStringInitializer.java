package com.example.pki.service;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class RandomStringInitializer {

    public static String generateAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public static String generatePin() {
        Random rand = new Random();

        log.debug("Pin generated!");
        return Integer.toString(rand.nextInt(9999 - 1000) + 1000);
    }
}
