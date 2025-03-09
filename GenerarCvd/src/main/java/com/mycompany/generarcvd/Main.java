
package com.mycompany.generarcvd;

public class Main {

    public static void main(String[] args) throws Exception {
        
        String cvd1 = CVD.genCVD();
        String cvd2 = CVD.genCVD();
        String cvd3 = CVD.genCVD();
        String cvd4 = CVD.genCVD();
        
        System.err.println("cvd1: " + cvd1);
        System.err.println("cvd2: " + cvd2);
        System.err.println("cvd3: " + cvd3);
        System.err.println("cvd4: " + cvd4);
    }
}
