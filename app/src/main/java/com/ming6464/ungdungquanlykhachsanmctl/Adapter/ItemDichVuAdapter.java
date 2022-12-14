package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemDichVuAdapter extends RecyclerView.Adapter<ItemDichVuAdapter.ViewHolder> {
    private Context context;
    private List<Services> listService;
    private NumberFormat format;

    public ItemDichVuAdapter(Context context, List<Services> listService) {
        this.context = context;
        this.listService = listService;
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }

    public void setData(List<Services> lists) {
        this.listService = lists;
        notifyDataSetChanged();
    }




    @Override
    public ItemDichVuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dich_vu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDichVuAdapter.ViewHolder holder, int position) {
        Services services = listService.get(position);
        holder.tv_price.setText(format.format(services.getPrice()) + "K");
        holder.tv_name.setText(services.getName());
        holder.tv_STT.setText(String.valueOf(position));
        if(position % 2 == 0)
            holder.linear_title.setBackgroundColor(Color.WHITE);
        else{
            holder.linear_title.setBackgroundResource(R.color.itemServicele);
        }
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_price,tv_STT;
        private LinearLayoutCompat linear_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemDichVu_name);
            tv_price = itemView.findViewById(R.id.itemDichVu_price);
            tv_STT = itemView.findViewById(R.id.itemDichVu_tv_stt);
            linear_title = itemView.findViewById(R.id.itemDichVu_linear_title);
        }
    }
}
