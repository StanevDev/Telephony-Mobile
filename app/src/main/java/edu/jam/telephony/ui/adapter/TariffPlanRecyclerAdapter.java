package edu.jam.telephony.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.jam.telephony.R;
import edu.jam.telephony.model.TariffPlan;

public class TariffPlanRecyclerAdapter
        extends RecyclerView.Adapter<TariffPlanRecyclerAdapter.ViewHolder>  {

    private final int currentPlan;
    private final List<TariffPlan> plans;
    private final TariffRecyclerInteractor listener;

    public TariffPlanRecyclerAdapter(List<TariffPlan> plans, TariffPlan current, TariffRecyclerInteractor listener) {
        this.plans = plans;
        this.listener = listener;
        currentPlan = plans.indexOf(current);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tariff_plan, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TariffPlan plan = plans.get(position);

        holder.setName(plan.getName());
        holder.setDescription(plan.getDescription());
        holder.setIsCurrent(position == currentPlan);

        holder.tariffCard.setOnClickListener(v -> {
            if (position != currentPlan)
                listener.onChangeRequested(plan);
        });
    }

    @Override
    public int getItemCount() {
        return plans == null ? 0 : plans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tariffName;
        TextView tariffDescription;
        ImageView isCurrentImage;
        CardView tariffCard;

        public ViewHolder(View v) {
            super(v);

            tariffName = v.findViewById(R.id.tariff_name);
            tariffDescription = v.findViewById(R.id.tariff_description);
            isCurrentImage = v.findViewById(R.id.is_current_image);
            tariffCard = v.findViewById(R.id.tariff_item_card);
        }

        void setName(String name){
            tariffName.setText(name);
        }

        void setDescription(String description){
            tariffDescription.setText(description);
        }

        void setIsCurrent(boolean isCurrent){
            if (isCurrent)
                isCurrentImage.setVisibility(View.VISIBLE);
            else
                isCurrentImage.setVisibility(View.INVISIBLE);
        }
    }

    public interface TariffRecyclerInteractor {
        void onChangeRequested(TariffPlan plan);
    }
}
