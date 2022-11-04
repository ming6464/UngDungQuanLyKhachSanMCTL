package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Categories {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int price,amountOfPeople;

    public Categories(String name, int price, int amountOfPeople) {
        this.name = name;
        this.price = price;
        this.amountOfPeople = amountOfPeople;
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

    public int getAmountOfPeople() {
        return amountOfPeople;
    }

    public void setAmountOfPeople(int amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }
}
