package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ming6464.ungdungquanlykhachsanmctl.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemCategoryThongKeAdapter extends RecyclerView.Adapter<ItemCategoryThongKeAdapter.MyViewHolder> {
    private List<String> listName;
    private int[] ArrSoLieu;
    private NumberFormat format;
    private int heightView = 0;

    public ItemCategoryThongKeAdapter(){
        format = NumberFormat.getInstance(new Locale("en","EN"));
    }

    public void setData(List<String> name,int[] ArrSoLieu,int heightView){
        this.listName = name;
        this.ArrSoLieu = ArrSoLieu;
        this.heightView = heightView;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_thongke,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int slThanhToan = ArrSoLieu[position * 3 + 1],
        total = ArrSoLieu[position * 3],
        slHuy = ArrSoLieu[position * 3 + 2];
        holder.tv_name.setText(listName.get(position));
        holder.tv_total.setText(format.format(total) + "K");

        holder.tv_statusSuccess.setVisibility(View.VISIBLE);
        holder.tv_statusSuccess.setText(String.valueOf(slThanhToan));
        ViewGroup.LayoutParams params = holder.tv_statusSuccess.getLayoutParams();
        params.height = slThanhToan * heightView;
        holder.tv_statusSuccess.setLayoutParams(params);

        holder.tv_statusCancel.setVisibility(View.VISIBLE);
        holder.tv_statusCancel.setText(String.valueOf(slHuy));
        params = holder.tv_statusCancel.getLayoutParams();
        params.height = slHuy * heightView;
        holder.tv_statusCancel.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        if(listName == null)
            return 0;
        return listName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_total,tv_name,tv_statusSuccess,tv_statusCancel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_total = itemView.findViewById(R.id.itemCategoryThongKe_tv_total);
            tv_name = itemView.findViewById(R.id.itemCategoryThongKe_tv_name);
            tv_statusCancel = itemView.findViewById(R.id.itemCategoryThongKe_tv_statusCancel);
            tv_statusSuccess = itemView.findViewById(R.id.itemCategoryThongKe_tv_statusSuccess);
        }
    }
}
