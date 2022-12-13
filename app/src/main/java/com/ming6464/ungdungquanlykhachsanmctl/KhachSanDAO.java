package com.ming6464.ungdungquanlykhachsanmctl;

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
import com.ming6464.ungdungquanlykhachsanmctl.DTO.ServiceOrder;
import com.ming6464.ungdungquanlykhachsanmctl.DTO.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Dao
public abstract class KhachSanDAO {
    // categories
    @Query("SELECT * FROM categories")
    public abstract List<Categories> getAllOfLoaiPhong();

    @Insert
    public abstract void insertOfLoaiPhong(Categories obj);

    //services
    @Insert
    public abstract void insertOfService(Services services);

    @Query("SELECT * FROM services WHERE id = :id")
    public abstract Services getObjOfServices(int id);

    @Query("SELECT * FROM Services")
    public abstract List<Services> getAllService();

    public List<Services> getListWithOrderDetailIdOfService(int id) {
        List<Services> list = new ArrayList<>();
        for (ServiceOrder x : getListWithOrderDetailIdOfServiceOrder(id)) {
            list.add(getObjOfServices(x.getServiceId()));
        }
        return list;
    }

    public List<Services> getListWithRoomIdOfServices(String id) {
        List<Services> list = new ArrayList<>();
        for (ServiceCategory x : getListWithCategoryIdOfServiceCategory(getObjOfRooms(id).getCategoryID())) {
            list.add(getObjOfServices(x.getServiceID()));
        }
        return list;
    }

    //User
    @Insert
    public abstract void insertOfUser(People people);

    @Query("SELECT * FROM People WHERE status = :status")
    public abstract List<People> getListWithStatusOfUser(int status);

    @Query("SELECT * FROM PEOPLE WHERE  SDT = :phoneNumber")
    public abstract People getObjOfUser(String phoneNumber);

    @Query("SELECT count(*) FROM PEOPLE WHERE  SDT = :phoneNumber")
    public abstract Integer getAmountWithPhoneNumberPeople(String phoneNumber);

    @Query("SELECT count(*) FROM PEOPLE WHERE  CCCD = :cccd")
    public abstract Integer getAmountWithCCCDPeople(String cccd);

    @Query("SELECT * FROM PEOPLE WHERE cccd = :CCCD_or_CMND")
    public abstract People getObjWithCCCDOfUser(String CCCD_or_CMND);

    @Update
    public abstract void UpdateUser(People people);

    @Delete
    public abstract void DeleteUser(People people);

    @Query("SELECT * FROM people WHERE id = :id")
    public abstract People getObjOfUser(int id);

    @Query("SELECT * FROM PEOPLE WHERE status = 0")
    public abstract List<People> getListKhachHangOfUser();

    @Query("select * from PEOPLE where SDT like :s and status = 0")
    public abstract List<People> searchKhachHangWithPhoneNumber(String s);

    @Query("select * from PEOPLE where SDT like :s and status !=0 AND status != 2")
    public abstract List<People> searchNhanVienWithPhoneNumber(String s);

    //serviceCategory
    @Insert
    public abstract void insertOfServiceCategory(ServiceCategory obj);

    @Query("SELECT * FROM ServiceCategory WHERE categoryID = :id")
    public abstract List<ServiceCategory> getListWithCategoryIdOfServiceCategory(int id);


    //Rooms
    @Insert
    public abstract void insertOfRooms(Rooms obj);

    @Query("SELECT * FROM Rooms WHERE id = :id")
    public abstract Rooms getObjOfRooms(String id);

    @Query("SELECT * FROM Rooms")
    public abstract List<Rooms> getAllOfRooms();

    @Query("SELECT price FROM Categories WHERE id = (SELECT categoryID  FROM Rooms WHERE id = :id)")
    public abstract int getPriceWithIdOfRooms(String id);

    @Query("SELECT * FROM rooms WHERE id in (SELECT roomID FROM orderdetail WHERE orderID = :id)")
    public abstract List<Rooms> getListWithOrderIdOfRooms(int id);

    //Orders
    @Insert
    public abstract void insertOfOrders(Orders obj);

    @Query("SELECT MAX(id) FROM Orders")
    public abstract int getNewIdOfOrders();

    public void updateTotalOfOrders(int id, int money) {
        Orders obj = getObjOfOrders(id);
        obj.setTotal(obj.getTotal() + money);
        updateOfOrders(obj);
    }

    @Query("SELECT * FROM Orders WHERE id = :id")
    public abstract Orders getObjOfOrders(int id);

    @Update
    public abstract void updateOfOrders(Orders obj);

    public void checkOutRoomOfOrder(int id, Date endDate) {
        Orders obj = getObjOfOrders(id);
        obj.setStatus(1);
        obj.setEndDate(endDate);
        updateOfOrders(obj);
        int orderNewId = -1;
        for (OrderDetail x : getListWithOrderIdOfOrderDetail(obj.getId())) {
            if (x.getStatus() == 0) {
                x.setStatus(1);
                updateOfOrderDetail(x);
            } else if (x.getStatus() == 2 || x.getStatus() == 3) {
                if (orderNewId < 0) {
                    insertOfOrders(new Orders(obj.getCustomID(), obj.getUID(), obj.getStartDate(), obj.getEndDate()));
                    orderNewId = getNewIdOfOrders();
                }
                x.setOrderID(orderNewId);
                updateOfOrderDetail(x);
                updateTotalOfOrders(orderNewId, getTotalPriceOrderDetail(orderNewId));
            }
        }
        if (orderNewId > 0)
            reLoadEndOfOrders(orderNewId);
    }


    @Query("SELECT * FROM Orders WHERE status = :status")
    public abstract List<Orders> getListWithStatusOfOrders(int status);

    @Query("SELECT * FROM ORDERS WHERE CUSTOMID = :peopleId AND status = 0 AND id IN (SELECT ORDERID FROM ORDERDETAIL WHERE checkIn = :checkIn GROUP BY ORDERID)")
    public abstract Orders getObjUnpaidWithPeopleIdfOrders(int peopleId, Date checkIn);

    public void reLoadEndOfOrders(int id) {
        Orders obj = getObjOfOrders(id);
        obj.setEndDate(getMaxEndDateOrders(id));
        updateOfOrders(obj);
    }

    //OrderDetail
    @Insert
    public abstract void insertObjOfOrderDetail(OrderDetail obj);

    @Update
    public abstract void updateOfOrderDetail(OrderDetail obj);

    @Query("SELECT * FROM orderdetail")
    public abstract List<OrderDetail> getAllOfOrderDetail();

    @Query("SELECT * FROM orderdetail WHERE orderID = :id")
    public abstract List<OrderDetail> getListWithOrderIdOfOrderDetail(int id);

    public void insertOfOrderDetail(OrderDetail obj) {
        insertObjOfOrderDetail(obj);
        int priceRooms = getPriceWithIdOfRooms(obj.getRoomID());
        int amount_date = (int) (obj.getCheckOut().getTime() - obj.getCheckIn().getTime()) / (3600000 * 24) + 1;
        updateTotalOfOrders(obj.getOrderID(), priceRooms * amount_date);
    }

    @Query("SELECT * FROM ORDERDETAIL WHERE ID = :id")
    public abstract OrderDetail getObjOrderDetail(int id);

    @Query("SELECT * FROM ORDERDETAIL WHERE ROOMID = :id")
    public abstract OrderDetail getWithRoomIdOfOrderDetail(String id);

    @Query("SELECT MAX(id) FROM orderdetail")
    public abstract int getNewIdOfOrderDetail();

    public void cancelOfOrderDetail(int id, Date endDate) {
        OrderDetail obj = getObjOrderDetail(id);
        obj.setStatus(4);
        updateOfOrderDetail(obj);
        boolean check = true;
        for (OrderDetail x : getListWithOrderIdOfOrderDetail(obj.getOrderID())) {
            if (x.getStatus() != 4) {
                check = false;
                break;
            }
        }
        if (check) {
            Orders orders = getObjOfOrders(obj.getOrderID());
            orders.setStatus(2);
            orders.setEndDate(endDate);
            updateOfOrders(orders);
        } else
            reLoadEndOfOrders(obj.getOrderID());
    }

    //serviceOrder
    @Insert
    public abstract void insertObjOfServiceOrder(ServiceOrder obj);

    @Query("SELECT * FROM SERVICEORDER WHERE serviceId = :idService AND  orderDetailID = :idOrderDetail")
    public abstract ServiceOrder getObjOfServiceOrder(int idService, int idOrderDetail);

    public void insertOfServiceOrder(ServiceOrder obj) {
        ServiceOrder x = getObjOfServiceOrder(obj.getServiceId(), obj.getOrderDetailID());
        if (x != null) {
            x.setAmount(x.getAmount() + obj.getAmount());
            updateOfServiceOrder(x);
        } else {
            insertObjOfServiceOrder(obj);
        }
        updateTotalOfOrders(getIdOrderWithIdOrderDetail(obj.getOrderDetailID()), getObjOfServices(obj.getServiceId()).getPrice() * obj.getAmount());
    }

    @Update
    public abstract void updateOfServiceOrder(ServiceOrder obj);

    @Query("SELECT * FROM SERVICEORDER WHERE ORDERDETAILID = :id")
    public abstract List<ServiceOrder> getListWithOrderDetailIdOfServiceOrder(int id);

    @Query("SELECT * FROM Categories WHERE id = (SELECT categoryID FROM Rooms WHERE id = :id)")
    public abstract Categories getCategoryWithRoomId(String id);

    @Query("SELECT * FROM services WHERE id in (SELECT serviceID FROM servicecategory WHERE categoryID = (SELECT categoryID FROM Rooms WHERE id = :id))")
    public abstract List<Services> getListServiceCategoryWithRoomId(String id);

    public List<String> getListNameCategoryWithRoomId(List<Rooms> roomsList) {
        List<String> list = new ArrayList<>();
        for (Rooms x : roomsList) {
            list.add(getCategoryWithRoomId(x.getId()).getName());
        }
        return list;
    }

    @Query("SELECT amountOfPeople FROM categories WHERE id = (SELECT categoryID FROM Rooms WHERE id = :id)")
    public abstract int getAmountOfPeopleCategoryWithRoomId(String id);

    @Query("SELECT orderID FROM orderdetail WHERE id = :id")
    public abstract int getIdOrderWithIdOrderDetail(int id);

    // check login
    @Query("SELECT * FROM People Where SDT = :user")
    public abstract People checkLogin(String user);


    //
    @Query("Select * from People where status =:sta and SDT = :sdt")
    public abstract People check(int sta, String sdt);

    //get data
    @Query("SELECT * FROM People Where SDT =:sdt")
    public abstract People getUserBy(String sdt);

    @Query("SELECT SUM(PRICE * AMOUNT) FROM SERVICES AS A, SERVICEORDER WHERE A.ID = SERVICEID AND ORDERDETAILID = :id")
    public abstract int getTotalServiceWithOrderDetailId(int id);

    @Query("SELECT ROOMID FROM ORDERDETAIL WHERE ((:checkIn BETWEEN checkIn AND checkOut) OR (checkIn BETWEEN :checkIn AND :checkOut)) AND (STATUS != 1 AND STATUS != 4)")
    public abstract List<String> getListRoomIdBusyWithTime(Date checkIn, Date checkOut);

    public List<Rooms> getListRoomWithTime(Date checkIn, Date checkOut) {
        List<Rooms> list = new ArrayList<>();
        if (checkIn.getTime() < checkOut.getTime()) {
            list = getAllOfRooms();
            List<String> listRoomId = getListRoomIdBusyWithTime(checkIn, checkOut);
            for (Rooms x : list) {
                if (listRoomId.contains(x.getId()))
                    x.setStatus(1);
                else
                    x.setStatus(0);
            }
        }
        return list;
    }

    @Query("SELECT MAX(CHECKOUT) FROM ORDERDETAIL WHERE ORDERID = :orderId AND STATUS != 4")
    public abstract Date getMaxEndDateOrders(int orderId);

    @Query("SELECT * FROM ORDERDETAIL WHERE ORDERID IN (SELECT id FROM ORDERS WHERE (ENDDATE BETWEEN :startDate AND :endDate) AND status != 0)")
    public abstract List<OrderDetail> getListOrderDetailWhenEndDateBetweenTime(Date startDate, Date endDate);

    public Integer getTotalPriceOrderDetail(int orderDetailId) {
        OrderDetail x = getObjOrderDetail(orderDetailId);
        int priceRooms = getPriceWithIdOfRooms(x.getRoomID());
        int amount_date = (int) (x.getCheckOut().getTime() - x.getCheckIn().getTime()) / (3600000 * 24) + 1;
        return priceRooms * amount_date;
    }
}
