package edu.jam.telephony.ui.framgent;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.jam.telephony.R;
import edu.jam.telephony.model.Service;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.ServiceApi;
import edu.jam.telephony.ui.adapter.ServiceRecyclerAdapter;
import edu.jam.telephony.ui.dialog.Dialogs;
import io.reactivex.disposables.Disposable;

public class ManageServicesFragment extends BaseFragment implements ServiceRecyclerAdapter.Interactor {

    @BindView(R.id.service_progress_layout) FrameLayout progress;
    @BindView(R.id.services_card)           CoordinatorLayout card;
    @BindView(R.id.service_recycler)        RecyclerView recycler;
    @BindView(R.id.manage_save_button)      FloatingActionButton saveButton;
    Unbinder unbinder;

    private ServiceRecyclerAdapter adapter;
    private List<Service> allServices;
    private ServiceApi api;

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        setLoadState(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manage_service, container, false);
        unbinder =  ButterKnife.bind(this, v);

        api = RetrofitService.createApi(ServiceApi.class);
        saveButton.setOnClickListener(v1 -> {getActivity().getSupportFragmentManager().popBackStack();});

        return v;
    }

    private void loadData() {
        Disposable d = api.getAll().subscribe(
                services -> allServices = services,
                defaultOnError,
                () -> api.getMyExtraServices().subscribe(
                        this::initRecycler,
                        defaultOnError,
                        () -> setLoadState(false)
                ));
        disposable(d);
    }


    private void initRecycler(List<Service> used) {
        adapter = new ServiceRecyclerAdapter(allServices, used,this);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
    }

    private void setLoadState(boolean isLoading){
        if (isLoading)
            progress.setVisibility(View.VISIBLE);
        else
            progress.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void showChangeServiceDialog(boolean isUsedNow, Service service) {
        Dialogs.showChangeServiceDialog(isUsedNow, service, getContext(),
                () -> {
                    if (isUsedNow){
                        Disposable d = api.disconnectService(service.getId()).subscribe(
                                disconnected -> adapter.onServiceDisconnected(disconnected),
                                defaultOnError
                        );
                        disposable(d);
                    } else {
                        Disposable d = api.connectService(service.getId()).subscribe(
                                connected -> adapter.onServiceConnected(connected),
                                defaultOnError);
                        disposable(d);
                    }
                });
    }
}
