package edu.jam.telephony.ui.framgent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.jam.telephony.R;
import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.model.Subscriber;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.AccountApi;
import edu.jam.telephony.ui.MainActivity;
import edu.jam.telephony.util.Utils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class AccountFragment extends Fragment {

    @BindView(R.id.account_label) TextView          accountName;
    @BindView(R.id.balance_label) TextView          balanceText;
    @BindView(R.id.number_label) TextView           phoneNumberText;
    @BindView(R.id.tariff_label) TextView           tariffText;
    @BindView(R.id.registration_label) TextView     registrationDate;
    @BindView(R.id.cost_label) TextView             totalCostText;
    @BindView(R.id.shimmer) RelativeLayout          layout;
    @BindView(R.id.account_card) CardView           accountCard;
    @BindView(R.id.account_progress) ProgressBar    accountProgress;
    @BindView(R.id.account_image) ImageView         image;
    @BindView(R.id.create_tech_request) Button      viewRequests;
    Unbinder unbinder;

    private AccountApi api;
    private Subscriber subscriber;
    private AccountSaver saver;
    private Disposable accountDisposable;
    private Disposable tariffDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, v);

        accountProgress.setVisibility(View.VISIBLE);
        accountCard.setVisibility(View.INVISIBLE);

        api = RetrofitService.createApi(AccountApi.class);
        saver = new AccountSaver(getContext());

        accountDisposable = api.getAccount().subscribe(
                subscriber1 -> {
                    subscriber = subscriber1;
                    updateUi();
                    loadPlan();
                    saver.save(subscriber1);
                },
                onError
        );

        viewRequests.setOnClickListener(
                v1 -> ((MainActivity) getActivity()).addTechRequestFragment());

        return v;
    }

    private void loadPlan() {
        tariffDisposable = api.getTariff(String.valueOf(subscriber.getTariffPlanId()))
                .subscribe(
                        tariffPlan -> tariffText.setText(tariffPlan.getName()),
                        onError);
    }

    @Override
    public void onDestroyView() {
        Utils.dispose(accountDisposable);
        Utils.dispose(tariffDisposable);
        unbinder.unbind();

        super.onDestroyView();
    }

    private void updateUi(){
        String balance = Utils.round(subscriber.getBalance());

        accountName.setText("John");
        balanceText.setText(balance);
        phoneNumberText.setText(String.valueOf(subscriber.getPhoneNumber()));
        registrationDate.setText(subscriber.getRegistrationDate());

        accountProgress.setVisibility(View.INVISIBLE);
        accountCard.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
    }

    private Consumer<Throwable> onError = throwable -> {
        Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
        throwable.printStackTrace();
    };

}
