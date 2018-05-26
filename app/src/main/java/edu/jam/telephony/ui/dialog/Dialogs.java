package edu.jam.telephony.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import edu.jam.telephony.model.Service;
import edu.jam.telephony.model.TariffPlan;

public class Dialogs {

    public static void showChangeTariffDialog(Context context, TariffPlan changeTo, ChangeTariffCallback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Change tariff plan");
        builder.setMessage("Now you want to change tariff plan to "
                + changeTo.getName()
                + " \nYou are sure?");
        builder.setPositiveButton("CHANGE", (dialog, which) -> {
            callback.changeTo(changeTo);
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        builder.create().show();
    }

    public interface ChangeTariffCallback {

        void changeTo(TariffPlan plan);

    }

    public static void showChangeServiceDialog(
            boolean isConnect,
            Service service,
            Context context,
            ChangeServiceCallback callback){
        String message = (!isConnect ? "Connect to " : "Disconnect from ") + service.getServiceName() + " service?";

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Manage service");
        builder.setMessage(message);
        builder.setPositiveButton(!isConnect ? "CONNECT" : "DISCONNECT",
                (dialog, which) -> callback.change());
        builder.setNegativeButton("CANCEL",
                (dialog, which) -> dialog.dismiss());
        builder.setCancelable(true);
        builder.create().show();

    }

    @FunctionalInterface
    public interface ChangeServiceCallback{

        void change();

    }

}
