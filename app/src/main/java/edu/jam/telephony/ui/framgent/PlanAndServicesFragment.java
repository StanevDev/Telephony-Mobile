package edu.jam.telephony.ui.framgent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;
import edu.jam.telephony.util.Utils;
import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.model.Service;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.ServiceApi;
import edu.jam.telephony.network.api.TariffApi;
import edu.jam.telephony.ui.MainActivity;
import edu.jam.telephony.ui.adapter.ServiceExpandableAdapter;
import io.reactivex.disposables.Disposable;


public class PlanAndServicesFragment extends BaseFragment {

    @BindView(R.id.services_expand)     ExpandableListView servicesExpandableView;
    @BindView(R.id.change_plan_im)      ImageView changePlan;
    @BindView(R.id.plan_name)           TextView planName;
    @BindView(R.id.plan_cost)           TextView planCost;
    @BindView(R.id.manage_service)      LinearLayout manageServices;

    private AccountSaver saver;
    private TariffApi tariffApi;
    private ServiceApi serviceApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan_service, container, false);
        ButterKnife.bind(this, v);

        tariffApi = RetrofitService.createApi(TariffApi.class);
        serviceApi = RetrofitService.createApi(ServiceApi.class);

        saver = new AccountSaver(getContext());

        manageServices.setOnClickListener(v1 -> ((MainActivity) getActivity()).addManagePlanFragment());

        changePlan.setOnClickListener(v1 -> ((MainActivity) getActivity()).addChangePlanFragmentFragment());
        servicesExpandableView.setVisibility(View.GONE);
        loadData();
        return v;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
       loadData();
    }

    private void initExpandList(List <Service> services) {
        servicesExpandableView.setVisibility(View.VISIBLE);

        ServiceExpandableAdapter adapter = new ServiceExpandableAdapter(services, getContext());
        servicesExpandableView.setAdapter(adapter);
    }

    public void loadData(){
        Disposable d = tariffApi.getTariff(saver.get().getTariffPlanId()).subscribe(
                plan -> {
                    planName.setText(plan.getName());
                    String price = Utils.round(plan.getPrice());
                    planCost.setText(price + "$");
                },
                defaultOnError);

        Disposable d1 = serviceApi.getMyExtraServices().subscribe(
                this::initExpandList,
                defaultOnError
        );

        disposable(d);
        disposable(d1);
    }

}
