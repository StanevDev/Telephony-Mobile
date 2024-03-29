package edu.jam.telephony.ui.framgent;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.jam.telephony.R;
import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.model.Subscriber;
import edu.jam.telephony.model.TariffPlan;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.TariffApi;
import edu.jam.telephony.ui.adapter.TariffPlanRecyclerAdapter;
import edu.jam.telephony.ui.dialog.Dialogs;
import io.reactivex.disposables.Disposable;


public class ChangePlanFragment extends BaseFragment
        implements TariffPlanRecyclerAdapter.TariffRecyclerInteractor,
                   Dialogs.ChangeTariffCallback {

    @BindView(R.id.tariff_recycler)     RecyclerView planRecycler;
    @BindView(R.id.change_progress)     ProgressBar progressBar;
    Unbinder unbinder;

    private TariffApi           api;
    private Subscriber          subscriber;
    private List<TariffPlan>    plans;
    private TariffPlan          currentPlan;
    private AccountSaver        saver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_plan, container, false);
        unbinder = ButterKnife.bind(this, v);

        subscriber = new AccountSaver(getContext()).get();
        api = RetrofitService.createApi(TariffApi.class);
        saver = new AccountSaver(getContext());

        planRecycler.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        setLoadingState(true);
        loadCurrentPlan();
        loadPlans();

        return v;
    }

    private void loadCurrentPlan() {
        Disposable d = api.getTariff(subscriber.getTariffPlanId()).subscribe(
                tariffPlan -> {
                    currentPlan = tariffPlan;
                    onDataPartLoaded();
                },
                defaultOnError
        );

        disposable(d);
    }

    private void loadPlans() {
        Disposable d = api.getTariffs().subscribe(
                plans -> {
                    this.plans = plans;
                    onDataPartLoaded();
                },
                defaultOnError
        );

        disposable(d);
    }

    private void onDataPartLoaded(){
        if (plans == null || currentPlan == null) return;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        TariffPlanRecyclerAdapter adapter = new TariffPlanRecyclerAdapter(plans, currentPlan, this);
        adapter.setHasStableIds(true);

        planRecycler.setLayoutManager(mLayoutManager);
        planRecycler.setItemAnimator(new DefaultItemAnimator());
        planRecycler.setAdapter(adapter);
        planRecycler.setHasFixedSize(true);

        setLoadingState(false);
    }

    private void setLoadingState(boolean isLoading){
        if (isLoading){
            planRecycler.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            planRecycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onChangeRequested(TariffPlan plan) {
        Dialogs.showChangeTariffDialog(
                getContext(),
                plan,
                this
        );
    }

    @Override
    public void changeTo(TariffPlan plan) {
        Disposable d = api.changeTariff(plan.getId()).subscribe(
                subscriber -> {
                    if (subscriber != null) {
                        Toast.makeText(getActivity(), "Plan changed", Toast.LENGTH_SHORT).show();
                        saver.save(subscriber);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    else
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                },
                defaultOnError);

        disposable(d);
    }
}
