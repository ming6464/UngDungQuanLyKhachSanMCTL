package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.CustomToast;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;
import com.ming6464.ungdungquanlykhachsanmctl.Fragment.DichVuFragment;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDAO;
import com.ming6464.ungdungquanlykhachsanmctl.KhachSanDB;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterDichVu extends RecyclerView.Adapter<AdapterDichVu.ViewHolder> {
    Context context;
    List<Services> listService;
    private NumberFormat format;

    public AdapterDichVu(Context context, List<Services> listService) {
        this.context = context;
        this.listService = listService;
        format = NumberFormat.getInstance(new Locale("vi","VN"));
    }

    public void setData(List<Services> lists) {
        this.listService = lists;
        notifyDataSetChanged();
    }




    @Override
    public AdapterDichVu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_dichvu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDichVu.ViewHolder holder, int position) {
        Services services = listService.get(position);
        holder.tvPrice.setText("Price :  " + format.format(services.getPrice()) + " đ");
        holder.tvName.setText("Name :  " + services.getName());
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        CardView cardViewService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cardViewService = itemView.findViewById(R.id.cardViewService);
        }
    }
}
