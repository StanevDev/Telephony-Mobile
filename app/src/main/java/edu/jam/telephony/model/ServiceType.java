package edu.jam.telephony.model;

public enum ServiceType {
    G3_INTERNET,
    G2_INTERNET,
    INTERNAL_CALL,
    EXTERNAL_CALL,
    SMS,
    MMS;


    public static ServiceType parse (String name) {
        switch (name) {
            case "3g_internet": return G3_INTERNET;
            case "2g_internet": return G2_INTERNET;
            case "internall_call": return INTERNAL_CALL;
            case "externall_call": return EXTERNAL_CALL;
            case "sms": return SMS;
            case "mms": return MMS;

            default: throw new RuntimeException("Can't parse " + name);
        }
    }
}