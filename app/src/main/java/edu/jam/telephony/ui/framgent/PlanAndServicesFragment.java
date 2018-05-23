package edu.jam.telephony.ui.framgent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;
import edu.jam.telephony.Utils;
import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.model.Service;
import edu.jam.telephony.model.ServiceType;
import edu.jam.telephony.model.TariffPlan;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.TariffApi;
import edu.jam.telephony.ui.MainActivity;
import edu.jam.telephony.ui.adapter.ServiceExpandableAdapter;
import io.reactivex.disposables.Disposable;


public class PlanAndServicesFragment extends BaseFragment {

    @BindView(R.id.services_expand)     ExpandableListView tariffsExpandableView;
    @BindView(R.id.change_plan_im)      ImageView changePlan;
    @BindView(R.id.plan_name)           TextView planName;
    @BindView(R.id.plan_cost)           TextView planCost;

    private AccountSaver saver;
    private TariffApi tariffApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan_service, container, false);
        ButterKnife.bind(this, v);
        initExpandList();

        tariffApi = RetrofitService.createApi(TariffApi.class);
        saver = new AccountSaver(getContext());

        changePlan.setOnClickListener(v1 -> ((MainActivity) getActivity()).addChangePlanFragmentFragment());
        loadData();
        return v;
    }

    private void initExpandList() {
        ServiceExpandableAdapter adapter = new ServiceExpandableAdapter(getServices(), getContext());
        tariffsExpandableView.setAdapter(adapter);
    }

    private void loadData(){
        Disposable d = tariffApi.getTariff(saver.get().getTariffPlanId()).subscribe(
                plan -> {
                    planName.setText(plan.getName());
                    String price = Utils.round(plan.getPrice());
                    planCost.setText(price + "$");
                },
                defaultOnError);

        disposable(d);
    }

    private List<Service> getServices(){
        List<Service> services = new ArrayList<>();

        services.add(new Service(0,new BigDecimal("124.00"),0, ServiceType.EXTERNAL_CALL,"Lorem"));
        services.add(new Service(0,new BigDecimal("24.50"),0, ServiceType.G3_INTERNET,"Neque"));
        services.add(new Service(0,new BigDecimal("114.00"),0, ServiceType.MMS,"Anyone"));
        services.add(new Service(0,new BigDecimal("4.00"),0, ServiceType.SMS,"Extremely"));
        return services;
    }


    private List<TariffPlan> getTariffs(){
        List<TariffPlan> tariffPlans = new ArrayList<>();
        tariffPlans.add(new TariffPlan(0,null,null,null,"Lorem",some));
        tariffPlans.add(new TariffPlan(0,null,null,null,"Accusamus",some));
        tariffPlans.add(new TariffPlan(0,null,null,null,"Extremely",some));
        tariffPlans.add(new TariffPlan(0,null,null,null,"Anyone",some));
        tariffPlans.add(new TariffPlan(0,null,null,null,"Neque",some));
        return tariffPlans;
    }

    private String some = "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or";
}
