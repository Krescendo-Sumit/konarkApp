package com.mytech.salesvisit.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytech.salesvisit.R;
import com.mytech.salesvisit.net.Visit;
import com.mytech.salesvisit.util.Util;

import java.util.List;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.ViewHolder> {

    List<Visit> mDataSet;
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public DashBoardAdapter(List<Visit> mDataSet) {
        this.mDataSet = mDataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getCustomer().setText(mDataSet.get(position).getCustomerName());
        holder.getCheckin().setText(Util.getTimeString(mDataSet.get(position).getCheckIn()));
        if(mDataSet.get(position).getCheckOut()!=null){
            holder.getRowView().setBackgroundColor(holder.getRowView().getResources().getColor(R.color.whiteColor));
            holder.getCheckout().setText(Util.getTimeString(mDataSet.get(position).getCheckOut()));
            holder.getCheckout().setOnClickListener(null);
        }else {
            holder.getRowView().setBackgroundColor(holder.getRowView().getResources().getColor(R.color.lightOrangeColor));
            holder.getCheckout().setText("InProgress");
            holder.getCheckout().setOnClickListener(onClickListener);
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItems(List<Visit> visits) {
            mDataSet.clear();
            mDataSet.addAll(visits);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtcustomername,txtcheckout,txtcheckin;
        private View rowView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
       /*     v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });*/
            rowView=v;
            txtcustomername = v.findViewById(R.id.tvlistCustomerName);
            txtcheckin=v.findViewById(R.id.tvlistChekin);
            txtcheckout=v.findViewById(R.id.tvlistChekout);

        }

        public TextView getCustomer() {
            return txtcustomername;
        }
        public TextView getCheckin() {
            return txtcheckin;
        }
        public TextView getCheckout() {
            return txtcheckout;
        }
        private View getRowView(){return rowView;}
    }
}
