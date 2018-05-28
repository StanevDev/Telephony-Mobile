package edu.jam.telephony.ui.framgent;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.jam.telephony.R;
import edu.jam.telephony.model.Service;
import edu.jam.telephony.model.ServiceType;
import edu.jam.telephony.util.StatisticLoader;
import io.reactivex.Observable;

import static android.graphics.Color.rgb;

public class StatisticFragment extends BaseFragment
        implements StatisticLoader.OnStatisticLoadedListener,
        OnChartValueSelectedListener{

    @BindView(R.id.pie_chart)   PieChart chart;
    @BindView(R.id.pay_service) TextView servicePay;
    @BindView(R.id.pay_tariff)  TextView tariffPay;
    @BindView(R.id.pay_total)   TextView totalCost;

    private StatisticLoader statsLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistic, container, false);
        ButterKnife.bind(this, v);

        statsLoader = new StatisticLoader(
                getContext(),
                this,
                defaultOnError);

        statsLoader.startLoading();

        initChart();
        return v;
    }

    private void initChart() {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setOnChartValueSelectedListener(this);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(12f);
    }

    private void setChartData(List<PieEntry> entries){
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }

    private static List<Integer> colors = new ArrayList<Integer>(){{
        add(rgb(84,110,122));
        add(rgb(251,140,0));
        add(rgb(0,137,123));
        add(rgb(94,53,177));
        add(rgb(46,125,50));
    }};

    @SuppressLint("CheckResult")
    @Override
    public void onDataLoaded(StatisticLoader.StatisticDto dto) {
        List<Service> allServices = new ArrayList<>(dto.services);
        allServices.addAll(dto.servicesFromTariff);
        Map<ServiceType, Float> byType = new HashMap<>();

        Observable.fromIterable(allServices).subscribe(
                service -> {
                    ServiceType type = service.getServiceType();
                    if (byType.containsKey(type)){
                        float value = byType.get(type);
                        byType.put(type, value + service.getPrice().floatValue());
                    } else {
                        byType.put(service.getServiceType(), service.getPrice().floatValue());
                    }
                }
        );

        setPayInfo(dto);
        setChartData(toPieEntry(byType));
    }

    @SuppressLint("SetTextI18n")
    private void setPayInfo(StatisticLoader.StatisticDto dto) {
        float tariffPayValue = dto.plan.getPrice().floatValue();
        float servicePayValue = (float) dto.totalCost - tariffPayValue;

        totalCost.setText(String.valueOf(dto.totalCost) + " $");
        tariffPay.setText(String.valueOf(dto.plan.getPrice().floatValue()) + " $");
        servicePay.setText(String.valueOf(servicePayValue) + " $");
    }

    private List<PieEntry> toPieEntry(Map<ServiceType, Float> map){
        List<PieEntry> entries = new ArrayList<>();

        for (ServiceType key : map.keySet()) {
            entries.add(new PieEntry(
                            map.get(key),
                            ServiceType.getPrettyName(key)
                    )
            );
        }
        return entries;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        chart.setCenterText("Cost\n" + String.valueOf(e.getY()) + " $");
    }

    @Override
    public void onNothingSelected() {

    }
}
