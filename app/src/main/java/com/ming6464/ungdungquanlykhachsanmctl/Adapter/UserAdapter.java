package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<People> mListUser;
    private IClickItemUser iClickItemUser;

    public UserAdapter(IClickItemUser iClickItemUser) {
        this.iClickItemUser = iClickItemUser;
    }

    public interface IClickItemUser {
        void updateUser(People people);

    }

    public void setData(List<People> list) {
        this.mListUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_chua, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        People people = mListUser.get(position);
        if (people == null) {
            return;
        }
        String gioiTinh = "nam";
        if(people.getSex() == 0)
            gioiTinh = "nữ";
        holder.tvName.setText("tên: " + people.getFullName());
        holder.tvSex.setText("giới tính: " + gioiTinh);
        holder.tvSdt.setText("sô điện thoại: " + people.getSDT());
        holder.tvCccd.setText("cmnd/CCCD: " + people.getCCCD());
        holder.tvAddress.setText("địa chỉ: " + people.getAddress());
        String status = "";
        if (people.getStatus()==0){
            holder.layoutUser.setBackgroundResource(R.color.user_binhThuong);
            status = "Bình Thường";
        }
        else if (people.getStatus()==2){
            holder.layoutUser.setBackgroundResource(R.color.hoadon_chuathanhtoan);
            status = "Đặt trước";
        }
        holder.tvStatus.setText("Trạng Thái: "+status);

        holder.layoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.updateUser(people);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListUser != null) {
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddress, tvName, tvSex, tvSdt, tvCccd,tvStatus;
        LinearLayoutCompat layoutUser;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_user);
            tvSex = itemView.findViewById(R.id.tv_sex);
            tvSdt = itemView.findViewById(R.id.tv_sdt);
            tvCccd = itemView.findViewById(R.id.tv_cccd);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvStatus = itemView.findViewById(R.id.tv_status);
            layoutUser = itemView.findViewById(R.id.layout_user);
        }
    }
}
