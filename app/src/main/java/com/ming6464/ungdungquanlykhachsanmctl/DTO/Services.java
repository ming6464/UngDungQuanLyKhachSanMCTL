package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Services {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int price;

    public Services(String name, int price) {
        this.name = name;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
