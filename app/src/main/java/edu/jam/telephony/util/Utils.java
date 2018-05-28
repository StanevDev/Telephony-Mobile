package edu.jam.telephony.util;

import java.math.BigDecimal;

import io.reactivex.disposables.Disposable;

public class Utils {

    public static String round(BigDecimal num){
        return num
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString();
    }

    public static void dispose(Disposable disposable){
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }
}
