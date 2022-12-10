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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhan_vien, parent, false);
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
        holder.tv_name.setText("Họ Tên :  " + people.getFullName());
        holder.tv_sex.setText("Giới Tính :  " + gioiTinh);
        holder.tv_sdt.setText("Số Điện Thoại :  " + people.getSDT());
        holder.tv_cccd.setText("CMND/CCCD :  " + people.getCCCD());
        holder.tv_address.setText("Địa chỉ :  " + people.getAddress());

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
        TextView tv_address, tv_name, tv_sex, tv_sdt, tv_cccd;
        ImageView img_avatar;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.itemNhanVien_tv_name);
            tv_sex = itemView.findViewById(R.id.itemNhanVien_tv_sex);
            tv_sdt = itemView.findViewById(R.id.itemNhanVien_tv_sdt);
            tv_cccd = itemView.findViewById(R.id.itemNhanVien_tv_cccd);
            tv_address = itemView.findViewById(R.id.itemNhanVien_tv_address);
            img_avatar = itemView.findViewById(R.id.itemNhanVien_img_avatar);
            itemView.findViewById(R.id.itemNhanVien_img_xoa).setVisibility(View.GONE);
            itemView.findViewById(R.id.itemNhanVien_tv_pass).setVisibility(View.GONE);
        }
    }
}
