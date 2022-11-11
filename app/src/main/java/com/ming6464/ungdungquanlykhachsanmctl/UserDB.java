package com.ming6464.ungdungquanlykhachsanmctl;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDB extends RoomDatabase {

    private  static final String DATABASE_NAME = "user.db";
    public static UserDB instance;
    public static synchronized UserDB getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDB.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract UserDAO userDAO();
}
