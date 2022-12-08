package com.ming6464.ungdungquanlykhachsanmctl;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceCategory;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;

@TypeConverters({Converters.class})
@Database(entities = {Categories.class, OrderDetail.class, Orders.class,
        People.class, Services.class, ServiceCategory.class, Rooms.class, ServiceOrder.class}, version = 1)
public abstract class KhachSanDB extends RoomDatabase {
    private static final String KHACHSAN_NAME = "khachsanmtcl";
    private static KhachSanDB instance;

    public static KhachSanDB getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, KhachSanDB.class, KHACHSAN_NAME).allowMainThreadQueries().build();
        return instance;
    }

    public abstract KhachSanDAO getDAO();

}
