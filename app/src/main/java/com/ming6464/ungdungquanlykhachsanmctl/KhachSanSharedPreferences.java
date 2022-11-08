package com.ming6464.ungdungquanlykhachsanmctl;

import android.content.Context;
import android.content.SharedPreferences;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;

public class KhachSanSharedPreferences {
    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private final String KEY_ID ="TK",KEY_MK = "MK",KEY_ID2 = "TK2",KEY_NAME ="NAME",CHECK_LOAI_PHONG = "LOAI_PHONG"
            ,NAME_SHARE = "KHACH_SAN_SHARE";

    public KhachSanSharedPreferences(Context context){
        share = context.getSharedPreferences(NAME_SHARE,Context.MODE_PRIVATE);
        editor = share.edit();
    }
    public void setAccount(People obj, boolean check){
        String id = String.valueOf(obj.getId());
        editor.putString(KEY_ID2,id);
        editor.putString(KEY_NAME,obj.getFullName());
        if(check){
            editor.putString(KEY_ID,id);
            editor.putString(KEY_MK,obj.getPassowrd());
        }else if(share.getString(KEY_ID,null)!= null && id.equals(share.getString(KEY_ID,null)))
            editor.remove(KEY_ID);
        editor.apply();
    }

    public void setCheckLoaiPhong(boolean check){
        editor.putBoolean(CHECK_LOAI_PHONG,check);
        editor.commit();
    }

    public boolean booleangetCheckLoaiPhong(){
        return share.getBoolean(CHECK_LOAI_PHONG,false);
    }

    public String getID2(){
        return share.getString(KEY_ID2,null);
    }
    public String getName(){return share.getString(KEY_NAME,null);}

    public String getPassword(){
        return share.getString(KEY_MK,null);
    }
    public String getID(){
        return share.getString(KEY_ID,null);
    }
}
