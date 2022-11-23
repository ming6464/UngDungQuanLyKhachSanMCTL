package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private List<People> mListUser;
    private IClickItemUser iClickItemUser;

    public UserAdapter(IClickItemUser iClickItemUser) {
        this.iClickItemUser = iClickItemUser;
    }

    public interface IClickItemUser {
        void updateUser(People people);

        void deleteUser(People people);
    }

    public UserAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<People> list) {
        this.mListUser = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_chua, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        People people = mListUser.get(position);
        if (people == null) {
            return;
        }
        holder.tvName.setText("Name: " + people.getFullName());
        holder.tvSex.setText("Sex: " + (people.getSex()));
        holder.tvSdt.setText("Number: " + people.getSDT());
        holder.tvCccd.setText("CCCD: " + people.getCCCD());
        holder.tvAddress.setText("Address: " + people.getAddress());
        String status = "";
        if (people.getStatus()==0){
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.user_binhThuong));
            status = "Bình Thường";
        }
        else if (people.getStatus()==1){
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.user_moiTao));
            status = "Mới Tạo";
        }
        else if (people.getStatus()==2){
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.datTruoc));
            status = "Đặt trước";
        }
        else if (people.getStatus()==3){
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.user_nhanVien));
            status = "Nhân Viên";
        }else {
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.user_addmin));
            status = "Admin";
        }
        holder.tvStatus.setText("Trạng Thái: "+status);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.deleteUser(people);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
        private Button btnUpdate, btnDelete;
        CardView cardView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_user);
            tvSex = itemView.findViewById(R.id.tv_sex);
            tvSdt = itemView.findViewById(R.id.tv_sdt);
            tvCccd = itemView.findViewById(R.id.tv_cccd);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvStatus = itemView.findViewById(R.id.tv_status);
            cardView = itemView.findViewById(R.id.card_update_user);
            btnDelete = itemView.findViewById(R.id.btnDelete_user);
        }
    }
}
