package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    public interface IClickItemUser{
        void updateUser(People people);
        void deleteUser(People people);
    }
    public UserAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<People> list){
        this.mListUser = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        People people = mListUser.get(position);
        if (people == null) {
            return;
        }
        holder.tvName.setText(people.getFullName());
        holder.tvSex.setText(String.valueOf(people.getSex()));
        holder.tvSdt.setText(people.getSDT());
        holder.tvCccd.setText(people.getCCCD());
        holder.tvAddress.setText(people.getAddress());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.updateUser(people);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.deleteUser(people);
            }
        });
    }
    @Override
    public int getItemCount() {
        if (mListUser != null){
            return mListUser.size();
        }
        return 0;
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvAddress,tvName,tvSex,tvSdt,tvCccd;
        private Button btnUpdate, btnDelete;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_user);
            tvSex = itemView.findViewById(R.id.tv_sex);
            tvSdt = itemView.findViewById(R.id.tv_sdt);
            tvCccd = itemView.findViewById(R.id.tv_cccd);
            tvAddress = itemView.findViewById(R.id.tv_address);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
