package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Rooms {
    @NonNull
    @PrimaryKey
    private String id;
    private int status,categoryID;

    public Rooms(String id,int categoryID) {
        this.categoryID = categoryID;
        this.id = id;
        this.status = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", categoryID=" + categoryID +
                '}';
    }
}
