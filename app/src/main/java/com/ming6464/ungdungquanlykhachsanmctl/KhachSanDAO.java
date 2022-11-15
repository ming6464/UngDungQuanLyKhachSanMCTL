package com.ming6464.ungdungquanlykhachsanmctl;

import android.util.Log;
import android.util.TimeUtils;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ming6464.ungdungquanlykhachsanmctl.DTO.Categories;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.OrderDetail;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Orders;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.People;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Rooms;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceCategory;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Dao
public abstract class KhachSanDAO {
    // Loại phòng
    @Query("SELECT * FROM categories")
    public abstract List<Categories> getAllOfLoaiPhong();
    @Insert
    public abstract void insertOfLoaiPhong(Categories obj);

    @Insert
    public abstract void insertOfService(Services services);

    @Query("SELECT * FROM Services")
    public abstract List<Services> getAllService();


    //User
    @Insert
    public abstract void insertOfUser(People people);

    @Query("SELECT * FROM People WHERE status = :status")
    public abstract List<People>getListWithStatusOfUser(int status);

    @Query("SELECT * FROM People")
    public abstract List<People> getListUser();

    public List<String> getListAdapterOfUser(List<People> peopleList){
        List<People> list = peopleList;
        List<String> stringList = new ArrayList<>();
        for(People x : list){
            stringList.add(formatId(x.getId()) + "  " + x.getFullName());
        }
        return stringList;
    }

    @Query("SELECT * FROM People where fullName= :fullName")
    public abstract List<People> checkUser(String fullName);

    @Update
    public abstract void UpdateUser(People people);

    @Delete
    public abstract void DeleteUser(People people);


    //serviceCategory
    @Insert
    public abstract void insertOfServiceCategory (ServiceCategory obj);

    @Query("SELECT * FROM ServiceCategory WHERE categoryID = :id")
    public abstract List<ServiceCategory> getListObjOfServiceCategory (int id);


    //Rooms
    @Insert
    public abstract void insertOfRooms (Rooms obj);

    @Query("SELECT * FROM Rooms WHERE id = :id")
    public abstract Rooms getWithIDOfRooms (int id);

    @Query("SELECT * FROM Rooms")
    public abstract List<Rooms> getAllOfRooms();

    public List<String> getListAdapterOfURooms(List<Rooms> roomsList){
        List<Rooms> list = roomsList;
        List<String> stringList = new ArrayList<>();
        for(Rooms x : list){
            stringList.add(formatId(x.getId()) + "  " + x.getName());
        }
        return stringList;
    }

    @Query("SELECT * FROM Rooms WHERE status = :status")
    public abstract List<Rooms> getListWithStatusOfRooms(int status);

    @Update
    public abstract void updateOfRooms (Rooms obj);

    @Query("SELECT price FROM Categories WHERE id = (SELECT categoryID  FROM Rooms WHERE id = :id)")
    public abstract int getPriceWithIdOfRooms (int id);

    public void updateStatusWithIdOfRooms(int id, int status){
        Rooms rooms = getWithIDOfRooms(id);
        rooms.setStatus(status);
        updateOfRooms(rooms);
    }

    //Orders
    @Insert
    public abstract  void insertOfOrders (Orders obj);

    @Query("SELECT * FROM Orders")
    public abstract List<Orders> getAllOfOrders();

    @Query("SELECT MAX(id) FROM Orders")
    public abstract  int getNewIdOfOrders ();

    @Query("SELECT id FROM Orders WHERE customID = :id")
    public abstract int getIdWithPeopleIdOfOrder (int id);

    public void updateTotalOfOrders(int id,int money){
        Orders obj = getWithIdOfOrders(id);
        obj.setTotal(obj.getTotal() + money);
        updateOfOrders(obj);
    }

    @Query("SELECT * FROM Orders WHERE id = :id")
    public abstract Orders getWithIdOfOrders(int id);

    @Update
    public abstract void updateOfOrders(Orders obj);

    public void thanhToanOfOrders(Orders obj){
        obj.setStatus(1);
        updateOfOrders(obj);
        for(OrderDetail x : getAllWithOrdersIdOfOrderDetail(obj.getId())){
            if(x.getStatus() == 0){
                checkOutOfOrderDetail(x);
            }
        }
    }

    //OrderDetail
    @Insert
    public abstract void insertObjOfOrderDetail (OrderDetail obj);

    @Query("SELECT * FROM OrderDetail WHERE orderID = :id")
    public abstract List<OrderDetail> getAllWithOrdersIdOfOrderDetail (int id);
    @Update
    public abstract void updateOfOrderDetail(OrderDetail obj);

    @Query("SELECT * FROM orderdetail")
    public abstract List<OrderDetail> getAllOfOrderDetail();

    public void insertOfOrderDetail (OrderDetail obj){
        insertObjOfOrderDetail(obj);
        updateStatusWithIdOfRooms(obj.getRoomID(), 1);
        int priceRooms = getPriceWithIdOfRooms(obj.getRoomID());
        int day = (int)TimeUnit.DAYS.convert(obj.getEndDate().getTime() - obj.getStartDate().getTime(),TimeUnit.MILLISECONDS);
        updateTotalOfOrders(obj.getOrderID(),priceRooms * day);
    }

    public void checkOutOfOrderDetail(OrderDetail obj){
        obj.setStatus(1);
        updateOfOrderDetail(obj);
        updateStatusWithIdOfRooms(obj.getRoomID() , 0);
    }
    @Delete
    public abstract void deleteObjOfOrderDetail (OrderDetail obj);

    public void deleteOfOrderDetail(OrderDetail obj){
        updateTotalOfOrders(obj.getOrderID(),getPriceWithIdOfRooms(obj.getRoomID()) * (-1));
        deleteObjOfOrderDetail(obj);
    }
    ////
    public String formatId(int id){
        if(id < 10)
            return "#0" + id;
        return "#" + id;
    }

    @Query("SELECT name FROM Categories WHERE id = (SELECT categoryID FROM Rooms WHERE id = :id)")
    public abstract String getNameCategoryWithRoomId(int id);

    public List<String> getListNameCategoryWithRoomId(List<Rooms> roomsList){
        List<String> list = new ArrayList<>();
        for(Rooms x : roomsList){
            list.add(getNameCategoryWithRoomId(x.getId()));
        }
        return list;
    }
}
