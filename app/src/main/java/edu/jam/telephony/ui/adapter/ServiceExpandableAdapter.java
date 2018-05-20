package edu.jam.telephony.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import edu.jam.telephony.R;
import edu.jam.telephony.Utils;
import edu.jam.telephony.model.Service;

public class ServiceExpandableAdapter extends BaseExpandableListAdapter {

    final List<Service> services;
    final Context context;

    public ServiceExpandableAdapter(List<Service> plans, Context context) {
        this.services = plans;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return services.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return services.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return services.get(groupPosition).getServiceName();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_service, null);
        }

        Service service = getService(groupPosition);

        convertView.setPadding(100,20, 0, 20);

        TextView title = convertView.findViewById(R.id.item_tariff_name);
        title.setText(service.getServiceName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_sub_tariff, null);
        }

        Service service = getService(groupPosition);

        TextView costView = convertView.findViewById(R.id.service_price_item);
        TextView typeView = convertView.findViewById(R.id.service_type_item);

        costView.setText(Utils.round(service.getPrice()));
        typeView.setText(service.getServiceType().name());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private Service getService(int position){
        return (Service) getGroup(position);
    }
}
