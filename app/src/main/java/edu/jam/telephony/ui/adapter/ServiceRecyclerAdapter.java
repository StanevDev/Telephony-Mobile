package edu.jam.telephony.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.jam.telephony.R;
import edu.jam.telephony.model.Service;
import edu.jam.telephony.model.ServiceType;

public class ServiceRecyclerAdapter extends RecyclerView.Adapter<ServiceRecyclerAdapter.ViewHolder> {

    private List<Service> allServices;
    private List<Service> userServices;
    private Interactor interactor;

    public ServiceRecyclerAdapter(List<Service> allServices, List<Service> userServices, Interactor interactor) {
        this.allServices = allServices;
        this.userServices = userServices;
    }

    public void replaceData (List<Service> allServices, List<Service> userServices){
        this.allServices = allServices;
        this.userServices = userServices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);
        return new ViewHolder(v);
    }

    public void onStateClicked(int position){
        boolean isUsingNow = userServices.contains(allServices.get(position));
        interactor.showChangeServiceDialog(isUsingNow, allServices.get(position).getServiceName());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = allServices.get(position);

        holder.setName(service.getServiceName());
        holder.setTypeAndValue(
                String.valueOf(service.getTarificationValue()),
                service.getServiceType()
        );
        holder.setIsUsing(
                userServices.contains(service)
        );
    }

    @Override
    public int getItemCount() {
        return allServices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView type;
        TextView value;
        ImageView action;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.service_name);
            type = itemView.findViewById(R.id.service_type);
            value = itemView.findViewById(R.id.service_value);
            action = itemView.findViewById(R.id.service_action_image);
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setTypeAndValue(String value, ServiceType type) {
            String valueString = value + ' ' +ServiceType.getValueAbb(type);

            this.value.setText(valueString);
            this.type.setText(ServiceType.getPrettyName(type));
        }

        public void setIsUsing(boolean isUsing){
            int res = isUsing ? R.drawable.ic_close : R.drawable.ic_add;
            action.setImageResource(res);
            action.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStateClicked(getAdapterPosition());
        }
    }

    public interface Interactor {

        void showChangeServiceDialog(boolean isUsedNow, String serviceName);

    }
}
