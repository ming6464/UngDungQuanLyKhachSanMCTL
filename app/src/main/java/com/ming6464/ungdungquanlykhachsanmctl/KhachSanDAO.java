package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;

import java.util.List;

@Dao
public abstract class KhachSanDAO {
    @Query("SELECT * FROM categories")
    public abstract List<Categories> getAllOfLoaiPhong();
    @Insert
    public abstract void insertOfLoaiPhong(Categories obj);

    @Insert
    public abstract void insertOfService(Services services);

    @Query("SELECT * FROM Services")
    public abstract List<Services> getAllService();

    @Insert
    public abstract void insertOfUser(People people);

    @Query("SELECT * FROM people")
    public abstract List<People>getListUser();

    @Query("SELECT * FROM people where fullName= :fullName")
    public abstract List<People> checkUser(String fullName);

    @Update
    public abstract void UpdateUser(People people);

    @Delete
    public abstract void DeleteUser(People people);
}
