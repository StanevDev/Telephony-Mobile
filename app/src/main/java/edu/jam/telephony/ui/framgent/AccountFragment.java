package edu.jam.telephony.ui.framgent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.jam.telephony.R;
import edu.jam.telephony.Utils;
import edu.jam.telephony.model.Subscriber;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.AccountApi;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.supercharge.shimmerlayout.ShimmerLayout;


public class AccountFragment extends Fragment {

    @BindView(R.id.account_label) TextView          accountName;
    @BindView(R.id.balance_label) TextView          balanceText;
    @BindView(R.id.number_label) TextView           phoneNumberText;
    @BindView(R.id.tariff_label) TextView           tariffText;
    @BindView(R.id.registration_label) TextView     registrationDate;
    @BindView(R.id.cost_label) TextView             totalCostText;
    @BindView(R.id.shimmer) ShimmerLayout           layout;
    @BindView(R.id.account_card) CardView           accountCard;
    @BindView(R.id.account_progress) ProgressBar    accountProgress;
    Unbinder unbinder;

    private AccountApi api;
    private Subscriber subscriber;
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

        accountDisposable = api.getAccount().subscribe(
                subscriber1 -> {
                    subscriber = subscriber1;
                    updateUi();
                    loadPlan();
                },
                onError
        );

        return v;
    }

    private void loadPlan() {
        tariffDisposable = api.getTariff(String.valueOf(subscriber.getTariffPlanId()))
                .subscribe(
                        tariffPlan -> {tariffText.setText(tariffPlan.getName());}
                );
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
    }

    private Consumer<Throwable> onError = throwable -> {
        Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
        throwable.printStackTrace();
    };

}
