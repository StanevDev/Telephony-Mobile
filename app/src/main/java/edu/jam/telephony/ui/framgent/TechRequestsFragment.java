package edu.jam.telephony.ui.framgent;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;
import edu.jam.telephony.model.TechRequest;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.TechRequestApi;
import edu.jam.telephony.ui.adapter.RequestRecyclerAdapter;
import edu.jam.telephony.ui.dialog.Dialogs;
import edu.jam.telephony.ui.dialog.RequestDialogFragment;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class TechRequestsFragment extends BaseFragment {

    @BindView(R.id.tech_loader)             FrameLayout loadingLayout;
    @BindView(R.id.tech_request_recycler)   RecyclerView recyclerView;
    @BindView(R.id.add_request)             FloatingActionButton addFab;

    private TechRequestApi api;
    private List<TechRequest> requestList;
    private RequestRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tech_requests, container, false);
        ButterKnife.bind(this, v);

        api = RetrofitService.createApi(TechRequestApi.class);

        loadData();

        addFab.setOnClickListener(
                v1 -> RequestDialogFragment
                        .create(this::createTechRequest)
                        .show(getChildFragmentManager(), "DIALOG")
        );
        return v;
    }

    private void createTechRequest(String description){
        Disposable d =
                api.addTechRequest(description)
                        .subscribe(techRequest1 -> {
                                    adapter.addRequest(techRequest1);
                                },
                                defaultOnError);
        disposable(d);
    }

    private void loadData(){
        addFab.setClickable(false);
        requestList = new ArrayList<>();

        Disposable d = api.getTechRequests().subscribe(
                techRequests -> this.requestList = techRequests,
                defaultOnError,
                this::initRecycler
        );

        disposable(d);
    }

    private void initRecycler(){
        adapter = new RequestRecyclerAdapter(requestList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        loadingLayout.setVisibility(View.GONE);
        addFab.setClickable(true);
    }

}
