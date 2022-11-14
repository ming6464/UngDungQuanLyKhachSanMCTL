package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Rooms {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int status,categoryID;

    public Rooms(String name,int categoryID) {
        this.categoryID = categoryID;
        this.name = name;
        this.status = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", categoryID=" + categoryID +
                '}';
    }
}
