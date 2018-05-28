package edu.jam.telephony.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.jam.telephony.R;
import edu.jam.telephony.model.TechRequest;

public class RequestRecyclerAdapter extends RecyclerView.Adapter<RequestRecyclerAdapter.ViewHolder> {

    private List<TechRequest> requests;

    public RequestRecyclerAdapter(List<TechRequest> requests) {
        this.requests = requests;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tech_request, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        TechRequest request = requests.get(position);
        h.setDate(request.getDate());
        h.setDescription(request.getProblemDescription());
        h.setStatus(true);
    }

    public void addRequest(TechRequest techRequest){
        requests.add(techRequest);
        notifyItemInserted(getItemCount() + 1);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView description;
        TextView statusText;
        ImageView statusIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tech_request_date);
            description = itemView.findViewById(R.id.request_description);
            statusText = itemView.findViewById(R.id.proceed_status_text);
            statusIcon = itemView.findViewById(R.id.proceed_status_ico);
        }

        void setDescription (String text) {
            description.setText(text);
        }

        void setDate(Date date){
            String adaptedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            this.date.setText(adaptedDate);
        }

        void setStatus(boolean isProceed){
            int src = isProceed ? R.drawable.ic_check : R.drawable.ic_check;
            String str = isProceed ? "Proceed" : "Not proceed";

            statusText.setText(str);
            statusIcon.setImageResource(src);
        }
    }

}
