package com.deepindersingh.atb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deepindersingh.atb.R;
import com.deepindersingh.atb.model.Requests;

import java.util.List;

/**
 * Created by deepindersingh on 28/05/17.
 */

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {

    private List<Requests> requests;
    private int rowLayout;
    private Context context;


    public static class RequestsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout requestsLayout;
        TextView donorName;
        TextView data;
        TextView email;
        TextView blood_group;
        TextView hospital;

        public RequestsViewHolder(View v) {
            super(v);
            requestsLayout = (LinearLayout) v.findViewById(R.id.requests_layout);
            donorName = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.phone);
            email = (TextView) v.findViewById(R.id.description);
            blood_group = (TextView) v.findViewById(R.id.group);
            hospital = (TextView) v.findViewById(R.id.hospital);
        }
    }

    public RequestsAdapter(List<Requests> requests, int rowLayout, Context context) {
        this.requests = requests;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public RequestsAdapter.RequestsViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RequestsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RequestsViewHolder holder, final int position) {
        holder.donorName.setText(requests.get(position).getName());
        holder.data.setText("Phone : " + requests.get(position).getMobile());
        holder.email.setText("Email : " + requests.get(position).getEmail());
        holder.blood_group.setText(requests.get(position).getBlood_group());
        holder.hospital.setText("Hospital : " + requests.get(position).getHospital_name());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
