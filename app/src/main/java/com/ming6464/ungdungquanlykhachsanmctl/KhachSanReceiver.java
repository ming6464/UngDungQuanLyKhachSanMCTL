package com.ming6464.ungdungquanlykhachsanmctl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;

import java.util.Date;
import java.util.List;

public class KhachSanReceiver extends BroadcastReceiver {
    private List<OrderDetail> list;
    private Long time;
    private String roomCheckOut,roomCancel;
    @Override
    public void onReceive(Context context, Intent intent) {
        roomCancel = "";
        roomCheckOut = "";
        time = System.currentTimeMillis() + 660000;
        time -= time % 3600000;
        Date date = new Date(time);
        list = KhachSanDB.getInstance(context).getDAO().getListOrderDetailCheckOut(date);
        for(OrderDetail x : list){
            if(x.getStatus() == 0)
                roomCheckOut += x.getRoomID() + ", ";
            else
                roomCancel += x.getRoomID()  + ", ";
        }
        if(!roomCancel.isEmpty())
            roomCancel = roomCancel.substring(0,roomCancel.length() - 1);
        if(!roomCheckOut.isEmpty())
            roomCheckOut = roomCheckOut.substring(0,roomCheckOut.length() - 1);
        Log.d("TAG.m.a", "Đến giờ trả phòng " +  roomCheckOut + "\n RoomCancel : " + roomCancel);
    }
}
