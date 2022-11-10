package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class useradapter extends RecyclerView.Adapter<useradapter.UserViewHolder> {
    private Context mContext;
    private List<user> mListUser;

    public useradapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<user> List){
        this.mListUser = List ;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item,parent,false );
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
      user user = mListUser.get(position);
      if (user == null){
          return;
      }
      holder.imgUser.setImageResource(user.getResourceId());
      holder.tvName.setText( user.getName());
    }

    @Override
    public int getItemCount() {
         if (mListUser !=null){
             return  mListUser.size();
         }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgUser;
        private TextView tvName;

        public UserViewHolder(@NonNull View itemView) {
            super( itemView );
            imgUser = itemView.findViewById(R.id.img_user);
            tvName =itemView.findViewById( R.id.tv_name );

        }
    }
}
