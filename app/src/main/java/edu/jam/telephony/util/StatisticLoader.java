package edu.jam.telephony.util;

import android.content.Context;

import java.util.List;

import edu.jam.telephony.model.AccountSaver;
import edu.jam.telephony.model.Service;
import edu.jam.telephony.model.Subscriber;
import edu.jam.telephony.model.TariffPlan;
import edu.jam.telephony.network.RetrofitService;
import edu.jam.telephony.network.api.ServiceApi;
import edu.jam.telephony.network.api.TariffApi;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class StatisticLoader {

    private final OnStatisticLoadedListener listener;
    private final Consumer<Throwable>       onError;
    private final AccountSaver              saver;
    private final StatisticDto              dto;
    private final CompositeDisposable       disposables;
    private final TariffApi                 tariffApi;
    private final ServiceApi                serviceApi;

    public StatisticLoader(Context context, OnStatisticLoadedListener listener, Consumer<Throwable> onError) {
        this.saver = new AccountSaver(context);
        this.listener = listener;
        this.onError = onError;
        disposables = new CompositeDisposable();
        dto = new StatisticDto();
        tariffApi = RetrofitService.createApi(TariffApi.class);
        serviceApi = RetrofitService.createApi(ServiceApi.class);
    }

    public void startLoading() {
        Subscriber sub = saver.get();

        Disposable d = tariffApi.getTariff(sub.getTariffPlanId())
                .subscribe(plan -> dto.plan = plan, onError, this::onPartLoaded);

        Disposable d1 = serviceApi.getMyExtraServices().
                subscribe(services -> dto.services = services, onError, this::onPartLoaded);

        disposables.add(d);
        disposables.add(d1);
    }

    public void cancel(){
        if (!disposables.isDisposed())
            disposables.dispose();
    }

    private void onPartLoaded(){
        if (!dto.isFull()) return;

        dto.totalCost = sumTotalCost(dto.plan, dto.services);
        listener.onDataLoaded(dto);
    }

    private double sumTotalCost(TariffPlan plan, List<Service> services){
        double cost = plan.getPrice().doubleValue();

        if (services.isEmpty()) return cost;

        for (Service s : services)
            cost += s.getPrice().doubleValue();

        return cost;
    }

    class StatisticDto {

        public TariffPlan plan;

        public List<Service> services;

        public double totalCost = 0d;

        boolean isFull(){
            return plan != null && services != null && totalCost != 0d;
        }

    }

    public interface OnStatisticLoadedListener {

        void onDataLoaded(StatisticDto dto);

    }

}
