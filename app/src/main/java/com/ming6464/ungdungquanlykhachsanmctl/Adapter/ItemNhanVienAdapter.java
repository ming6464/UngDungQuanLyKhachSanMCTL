package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class ItemNhanVienAdapter extends RecyclerView.Adapter<ItemNhanVienAdapter.ViewHolder> {
    private List<People> listNv;
    private EventOfItemNhanVienAdapter action;

    public ItemNhanVienAdapter(EventOfItemNhanVienAdapter action) {
        this.action = action;
    }

    public void setData(List<People> list){
        this.listNv = list;
        notifyDataSetChanged();
    }

    @Override
    public ItemNhanVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhan_vien, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNhanVienAdapter.ViewHolder holder, int position) {
        People people = listNv.get(holder.getAdapterPosition());
        holder.tv_name.setText("Họ và tên : " + people.getFullName());
        holder.tv_cccd.setText("CCCD : " + people.getCCCD());
        holder.tv_sdt.setText("Số Đt : " + people.getSDT());
        if (people.getSex() == 1) {
            holder.tv_sex.setText("Giới Tính :  Nam");
        } else {
            holder.tv_sex.setText("Giới Tính :  Nữ");
            holder.img_avatar.setImageResource(R.drawable.businesswoman_100);
        }
        holder.tv_address.setText("Địa Chỉ :  " + people.getAddress());
        if(people.getStatus() == 0){
            holder.img_xoa.setVisibility(View.GONE);
            holder.tv_pass.setVisibility(View.GONE);
        }else
            holder.tv_pass.setText("Mật Khẩu :  " + people.getPassowrd());
        holder.img_xoa.setOnClickListener(v -> {
            action.onDelete(holder.getAdapterPosition());
        });
        holder.itemView.setOnClickListener(v -> {
            action.onUpdate(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        if (listNv == null)
            return 0;
        return listNv.size();
    }
    public interface EventOfItemNhanVienAdapter{
        void onUpdate(int position);
        void onDelete(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_sdt, tv_cccd, tv_sex, tv_pass, tv_address;
        ImageView img_xoa, img_avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemNhanVien_tv_name);
            tv_sdt = itemView.findViewById(R.id.itemNhanVien_tv_sdt);
            tv_cccd = itemView.findViewById(R.id.itemNhanVien_tv_cccd);
            tv_sex = itemView.findViewById(R.id.itemNhanVien_tv_sex);
            tv_pass = itemView.findViewById(R.id.itemNhanVien_tv_pass);
            tv_address = itemView.findViewById(R.id.itemNhanVien_tv_address);
            img_xoa = itemView.findViewById(R.id.itemNhanVien_img_xoa);
            img_avatar = itemView.findViewById(R.id.itemNhanVien_img_avatar);
        }
    }
}
