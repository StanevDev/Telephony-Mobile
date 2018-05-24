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

    public static String getValueAbb(ServiceType type){
        switch (type){
            case MMS:
                return "mms";
            case SMS:
                return "sms";
            case G2_INTERNET:
                return "Mb";
            case G3_INTERNET:
                return "Mb";
            case EXTERNAL_CALL:
                return "min";
            case INTERNAL_CALL:
                return "min";
                default:return "";
        }
    }

    public static String getPrettyName(ServiceType type) {
        switch (type) {
            case MMS:
                return "MMS";
            case SMS:
                return "SMS";
            case G2_INTERNET:
                return "2G Internet";
            case G3_INTERNET:
                return "3G Internet";
            case EXTERNAL_CALL:
                return "External call";
            case INTERNAL_CALL:
                return "Internal call";
            default:
                return "";

        }
    }
}