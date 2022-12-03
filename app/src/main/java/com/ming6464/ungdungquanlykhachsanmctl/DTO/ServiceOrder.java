package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ServiceOrder {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private int serviceId,orderDetailID, amount;

    public ServiceOrder(int serviceId, int orderDetailID, int amount) {
        this.serviceId = serviceId;
        this.orderDetailID = orderDetailID;
        this.amount = amount;
    }

    public ServiceOrder() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }
}
