package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void inserUser(User user);

    @Query("SELECT * FROM user")
    List<User>getListUser();

    @Query("SELECT * FROM user where username= :username")
    List<User> checkUser(String username);

    @Update
    void UpdateUser(User user);

    @Delete
    void DeleteUser(User user);
}
