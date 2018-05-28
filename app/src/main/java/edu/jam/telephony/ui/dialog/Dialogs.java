package edu.jam.telephony.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import edu.jam.telephony.R;
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
            dialog.dismiss();
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

    public static void showRequestDialog(Context context, OnDescriptionEntered callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Create new request");

        final EditText input = new EditText(context);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setLines(5);
        input.setMaxLines(5);
        input.setGravity(Gravity.LEFT | Gravity.TOP);

        FrameLayout container = new FrameLayout(context);
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        input.setHint("Describe you problem");

        input.setLayoutParams(params);
        container.addView(input);

        builder.setView(container);

        builder.setPositiveButton("Create", (dialog, whichButton) -> {
            String description = input.getText().toString();
            callback.onEntered(description);
        });

        builder.setNegativeButton("Cancel",
                (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @FunctionalInterface
    public interface OnDescriptionEntered{

        void onEntered (String description);

    }

}
