package com.ming6464.ungdungquanlykhachsanmctl;

import android.content.Context;
import android.content.SharedPreferences;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

public class KhachSanSharedPreferences {
    private final SharedPreferences share;
    private final SharedPreferences.Editor editor;
    private final String KEY_ID ="TK",KEY_MK = "MK",KEY_SDT = "SDT",KEY_SDT2 = "SDT2",KEY_NAME ="NAME",
            CHECK_1 = "CHECK_1",CHECK_2 = "CHECK_2";

    public KhachSanSharedPreferences(Context context){
        share = context.getSharedPreferences("KHACH_SAN_SHARE",Context.MODE_PRIVATE);
        editor = share.edit();
    }
    public void setAccount(People obj, boolean check){
        editor.putInt(KEY_ID,obj.getId());
        editor.putString(KEY_SDT2,obj.getSDT());
        editor.putString(KEY_NAME,obj.getFullName());
        if(check){
            editor.putString(KEY_SDT,obj.getSDT());
            editor.putString(KEY_MK,obj.getPassowrd());
        }else if(obj.getSDT().equals(share.getString(KEY_SDT,null)))
            editor.remove(KEY_SDT);
        editor.apply();
    }

    public void setCheck1(boolean check){
        editor.putBoolean(CHECK_1,check);
        editor.commit();
    }
    public boolean getCheck1(){
        return share.getBoolean(CHECK_1,false);
    }
    public void setCheck2(boolean check){
        editor.putBoolean(CHECK_2,check);
        editor.commit();
    }
    public boolean getCheck2(){
        return share.getBoolean(CHECK_2,false);
    }
    public String getSDT2(){
        return share.getString(KEY_SDT2,null);
    }
    public String getSDT(){
        return share.getString(KEY_SDT,null);
    }
    public String getName(){return share.getString(KEY_NAME,null);}
    public String getPassword(){
        return share.getString(KEY_MK,null);
    }
    public Integer getID(){
        return share.getInt(KEY_ID,0);
    }
}
