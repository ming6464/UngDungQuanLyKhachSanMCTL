package com.ming6464.ungdungquanlykhachsanmctl.Hienthiphong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.ming6464.ungdungquanlykhachsanmctl.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvUser ;
    private useradapter useradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.frament_phong);
        rcvUser = findViewById(R.id.rcv_user);
        useradapter= new useradapter( this );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this , RecyclerView.VERTICAL,false );
        rcvUser.setLayoutManager(linearLayoutManager);
        useradapter.setData( getListUser() );
        rcvUser.setAdapter(useradapter);

    }
    private List<user> getListUser(){
        List<user> list = new ArrayList<>();
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 1 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 2 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 3 "));
        list.add(new user( R.drawable.ic_launcher_foreground,"Phòng 4 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 5 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 6 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 7 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 8 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 9 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 10 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 11 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 12 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 13 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 14 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 15 "));
        list.add(new user( R.drawable.ic_launcher_foreground ,"Phòng 16 "));

        return list;
    }
}