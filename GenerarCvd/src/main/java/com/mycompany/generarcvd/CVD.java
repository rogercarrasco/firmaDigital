
package com.mycompany.generarcvd;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CVD {
    
    public static String genCVD() throws Exception {
        
        String zeros = "000000000000";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date ene2021 = sdf.parse("01/01/2021");
        long time = (new Date()).getTime() - ene2021.getTime();
        
        // version
        String version = "0";
        
        // time
        String times = String.valueOf(time);
        if (times.length() < 12) {
            int dif = 12 - times.length();
            times = zeros.substring(0, dif) + times;
        } else {
            int dif = times.length() - 12;
            times = times.substring(dif);
        }
        
        // sufix
        String sufix;
        SecureRandom random = new SecureRandom();
        int ran = random.nextInt(100);
        if (ran > 9) {
            sufix = String.valueOf(ran);
        } else {
            sufix = '0' + String.valueOf(ran);
        }
        
        // assembling
        String vts = version + times + sufix;
        String checkDigit = String.valueOf(calculateLuhnsCheckDigit(vts));
        StringBuilder cvd = new StringBuilder(vts + checkDigit);
        
        // formatting
        cvd.insert(4, " ");
        cvd.insert(9, " ");
        cvd.insert(14, " ");
        return cvd.toString();
    }

    //
    // Codigo extraido de https://stackoverflow.com/questions/26383926/how-do-i-implement-the-luhn-algorithm
    //
    /**
    * Generates the check digit for a number using Luhn's algorithm described in detail at the following link:
    * https://en.wikipedia.org/wiki/Luhn_algorithm
    *
    * In short the digit is calculated like so:
    * 1. From the rightmost digit moving left, double the value of every second digit. If that value is greater than 9,
    *    subtract 9 from it.
    * 2. Sum all of the digits together
    * 3. Multiply the sum by 9 and the check digit will be that value modulo 10.
    *
    * @param number the number to get the Luhn's check digit for
    * @return the check digit for the given number
    */
    private static int calculateLuhnsCheckDigit(String digits) {
        
        int sum = 0;
        boolean alternate = false;
        for (int i = digits.length() - 1; i >= 0; --i) {
            int digit = Character.getNumericValue(digits.charAt(i)); // get the digit at the given index
            digit = (alternate = !alternate) ? (digit * 2) : digit; // double every other digit
            digit = (digit > 9) ? (digit - 9) : digit; // subtract 9 if the value is greater than 9
            sum += digit; // add the digit to the sum
        }
        return (sum * 9) % 10;
    }
    
}
