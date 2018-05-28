package edu.jam.telephony.ui.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;
import io.reactivex.functions.Consumer;

public class RequestDialogFragment extends DialogFragment {

    @BindView(R.id.new_description)     EditText description;
    @BindView(R.id.descriptionEntered)  FloatingActionButton fab;

    private Consumer<String> onDescriptionEntered;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_tech_request_create, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this,v);

        fab.setOnClickListener(v1 -> {
            if (TextUtils.isEmpty(description.getText()))
                description.setError("Empty description");
            else {
                try {
                    onDescriptionEntered.accept(description.getText().toString());
                    this.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Im sorry, we have an error", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        return v;
    }

    public static RequestDialogFragment create(Consumer<String> onDescriptionEntered){

        RequestDialogFragment dialog = new RequestDialogFragment();
        dialog.onDescriptionEntered = onDescriptionEntered;
        return dialog;
    }
}
