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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<People> mListUser;
    private IClickItemUser iClickItemUser;
    private View view;

    public UserAdapter(IClickItemUser iClickItemUser) {
        this.iClickItemUser = iClickItemUser;
    }

    public interface IClickItemUser {
        void updateUser(int position);

    }

    public void setData(List<People> list) {
        this.mListUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_chua, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        People people = mListUser.get(position);
        if (people == null) {
            return;
        }
        String gioiTinh = "Nam";
        if(people.getSex() == 0)
            gioiTinh = "Nữ";
        holder.tvName.setText("Họ Tên :  " + people.getFullName());
        holder.tvSex.setText("Giới Tính :  " + gioiTinh);
        holder.tvSdt.setText("Số Điện Thoại :  " + people.getSDT());
        holder.tvCccd.setText("CMND/CCCD :  " + people.getCCCD());
        holder.tvAddress.setText("Địa chỉ :  " + people.getAddress());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.updateUser(holder.getAdapterPosition());
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
        TextView tvAddress, tvName, tvSex, tvSdt, tvCccd;
        ImageView imgAvatar;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_user);
            tvSex = itemView.findViewById(R.id.tv_sex);
            tvSdt = itemView.findViewById(R.id.tv_sdt);
            tvCccd = itemView.findViewById(R.id.tv_cccd);
            tvAddress = itemView.findViewById(R.id.tv_address);
            imgAvatar = itemView.findViewById(R.id.itemUserChua_img_avatar);
        }
    }
}
