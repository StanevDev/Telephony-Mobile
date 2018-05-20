package edu.jam.telephony.ui.framgent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;


public class AccountFragment extends Fragment {

    @BindView(R.id.account_label) TextView      accountName;
    @BindView(R.id.balance_label) TextView      balanceText;
    @BindView(R.id.number_label) TextView       phoneNumberText;
    @BindView(R.id.tariff_label) TextView       tariffText;
    @BindView(R.id.registration_label) TextView registrationDate;
    @BindView(R.id.cost_label) TextView         totalCostText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

}
