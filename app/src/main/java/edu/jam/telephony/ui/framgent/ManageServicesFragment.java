package edu.jam.telephony.ui.framgent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.jam.telephony.R;
import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.ServiceApi;

public class ManageServicesFragment extends BaseFragment {

    @BindView(R.id.service_progress_layout) FrameLayout progress;
    @BindView(R.id.services_card)           CardView card;
    @BindView(R.id.service_recycler)        RecyclerView recycler;
    @BindView(R.id.manage_save_button)      Button saveButton;
    Unbinder unbinder;

    ServiceApi api;

    private AccountSaver saver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manage_service, container, false);
        unbinder =  ButterKnife.bind(this, v);

        api = RetrofitService.createApi(ServiceApi.class);
        saver = new AccountSaver(getContext());

        loadData();

        return v;
    }

    private void loadData() {
        initRecycler();
    }

    private void initRecycler() {

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
