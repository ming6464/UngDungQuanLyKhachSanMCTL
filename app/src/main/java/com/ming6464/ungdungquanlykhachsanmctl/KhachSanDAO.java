package com.ming6464.ungdungquanlykhachsanmctl;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;

import java.util.List;

@Dao
public abstract class KhachSanDAO {
    @Query("SELECT * FROM categories")
    public abstract List<Categories> getAllOfLoaiPhong();
    @Insert
    public abstract void insertOfLoaiPhong(Categories obj);
}
