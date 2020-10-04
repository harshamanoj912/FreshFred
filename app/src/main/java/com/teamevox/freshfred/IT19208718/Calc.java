package com.teamevox.freshfred.IT19208718;

public class Calc {

    public static double calculateCommission(double tmpVal, double totalPriceForTheOrder){
        return totalPriceForTheOrder * (tmpVal / 100.0);
    }

}
