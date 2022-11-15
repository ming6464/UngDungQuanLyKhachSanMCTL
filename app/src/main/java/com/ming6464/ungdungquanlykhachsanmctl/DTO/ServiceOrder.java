package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ServiceOrder {
    @PrimaryKey (autoGenerate = true)
    private int id;

    private int serviceId,orderDetailID;

    public ServiceOrder(int serviceId, int orderDetailID) {
        this.serviceId = serviceId;
        this.orderDetailID = orderDetailID;
    }

    public ServiceOrder() {
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
