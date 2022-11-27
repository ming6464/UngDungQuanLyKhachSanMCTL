package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class People implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String fullName, SDT, CCCD, address, passowrd;
    private int sex, status;

    public People(String fullName, String SDT, String CCCD, String address, int sex, int status) {
        this.fullName = fullName;
        this.SDT = SDT;
        this.CCCD = CCCD;
        this.address = address;
        this.sex = sex;
        this.status = status;
    }

    public People() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id +
                ", fullName='" + fullName + '\'' +
                ", SDT='" + SDT + '\'' +
                ", CCCD='" + CCCD + '\'' +
                ", address='" + address + '\'' +
                ", passowrd='" + passowrd + '\'' +
                ", sex=" + sex +
                ", status=" + status;
    }
}
