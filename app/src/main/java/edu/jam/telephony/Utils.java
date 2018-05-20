package edu.jam.telephony;

import java.math.BigDecimal;

public class Utils {

    public static String round(BigDecimal num){
        return num
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString();
    }
}
