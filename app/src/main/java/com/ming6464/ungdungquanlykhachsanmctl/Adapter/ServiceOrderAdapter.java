package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class ServiceOrderAdapter extends RecyclerView.Adapter<ServiceOrderAdapter.MyViewHolder>{
    private List<String> list;
    private EventOfServiceOrder action;
    private View view;

    public ServiceOrderAdapter(EventOfServiceOrder action){
        this.action = action;
    }
    public void setData(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_order,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position));
        holder.imgBtn_cancel.setOnClickListener(v -> action.cancel(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        if(list == null)
        return 0;
        return list.size();
    }

    public interface EventOfServiceOrder{
        void cancel(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageButton imgBtn_cancel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemServiceOrder_tv_name);
            imgBtn_cancel = itemView.findViewById(R.id.itemServiceOrder_imgBtn_cancel);
        }
    }
}
