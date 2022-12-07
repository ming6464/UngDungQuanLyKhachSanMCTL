package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class OrderDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String roomID;
    private int orderID,status,amountOfPeople,prepay;
    private Date startDate,endDate;

    public OrderDetail(String roomID, int orderID, int amountOfPeople, Date startDate, Date endDate) {
        this.roomID = roomID;
        this.orderID = orderID;
        this.status = 0;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amountOfPeople = amountOfPeople;
        this.prepay = 0;
    }

    public int getAmountOfPeople() {
        return amountOfPeople;
    }

    public void setAmountOfPeople(int amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPrepay() {
        return prepay;
    }

    public void setPrepay(int prepay) {
        this.prepay = prepay;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", roomID=" + roomID +
                ", orderID=" + orderID +
                ", status=" + status +
                ", amountOfPeople=" + amountOfPeople +
                ", startDate=" + startDate.toString() +
                ", endDate=" + endDate.toString();
    }
}
