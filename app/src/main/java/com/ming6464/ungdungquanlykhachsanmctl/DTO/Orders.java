package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Orders implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int customID,UID,status,total;

    public Orders(int customID, int UID) {
        this.customID = customID;
        this.UID = UID;
        this.status = 0;
        this.total = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomID() {
        return customID;
    }

    public void setCustomID(int customID) {
        this.customID = customID;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
