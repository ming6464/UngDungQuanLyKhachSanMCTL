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

import java.util.ArrayList;
import java.util.List;

public class AdapterDichVu extends RecyclerView.Adapter<AdapterDichVu.ViewHolder> {
    Context context;
    List<Services> listService;

    public AdapterDichVu(Context context, List<Services> listService) {
        this.context = context;
        this.listService = listService;
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
        if (services == null) {
            return;
        }
        holder.tvPrice.setText("Price: " + listService.get(holder.getAdapterPosition()).getPrice());
        holder.tvName.setText("Name: " + listService.get(holder.getAdapterPosition()).getName());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Xác nhận xóa dịch vụ")
                        .setMessage("Bạn chắc chắn muốn xóa không?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                KhachSanDB.getInstance(v.getContext()).getDAO().deleteDichVu(services);
                                CustomToast.makeText(v.getContext(), "Xóa thành công!", true).show();

                                listService = KhachSanDB.getInstance(v.getContext()).getDAO().getAllService();
                                setData(listService);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        CardView cardViewService;
        Button btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            cardViewService = itemView.findViewById(R.id.cardViewService);
            btn_delete = itemView.findViewById(R.id.btn_delete_dichvu);
        }
    }
}
