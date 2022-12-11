package com.ming6464.ungdungquanlykhachsanmctl.Adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.List;

public class ItemRoomAdapter extends RecyclerView.Adapter<ItemRoomAdapter.RoomsViewHolder>{

    private List<Rooms> mListRooms;
    private List<String> listCategory;
    private IClickItemRooms iClickItemRooms;

    public void setData(List<Rooms> list,List<String> listCategory){
        this.mListRooms = list;
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }
    public ItemRoomAdapter(IClickItemRooms iClickItemRooms) {
        this.iClickItemRooms = iClickItemRooms;
    }
    public interface IClickItemRooms {
        void datPhong(Rooms rooms);
    }
    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rooms,parent,false);
        return new RoomsViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RoomsViewHolder holder, int position) {
        Rooms rooms = mListRooms.get(position);
        if(rooms == null){
            return;
        }
        holder.tv_room_name.setText("" + rooms.getId());
        holder.tv_room_catogory.setText(listCategory.get(position));
        if (rooms.getStatus() == 0)
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.phongTrong));
        else if (rooms.getStatus() == 1)
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.coNguoi));
        else
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.datTruoc));

        holder.cardView.setOnClickListener(v -> iClickItemRooms.datPhong(rooms));


    }
    @Override
    public int getItemCount() {
        if (mListRooms != null){
            return mListRooms.size();
        }
        return 0;
    }

    public class RoomsViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_room_name,tv_room_catogory;
        CardView cardView;

        public RoomsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_room_name = itemView.findViewById(R.id.itemRooms_tv_name);
            cardView = itemView.findViewById(R.id.card_rooms);
            tv_room_catogory = itemView.findViewById(R.id.itemRooms_tv_category);
        }
    }
}
