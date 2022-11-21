package com.ming6464.ungdungquanlykhachsanmctl.DTO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HoaDon {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tenKh,ngayNhan,ngayTra,tenPhong,dichVu;
    private int tongTien;
    private String status;

    public HoaDon(int id, String tenKh, String ngayNhan, String ngayTra, String tenPhong, String dichVu,String status, int tongTien) {
        this.id = id;
        this.tenKh = tenKh;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.tenPhong = tenPhong;
        this.dichVu = dichVu;
        this.status = status;
        this.tongTien = tongTien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(String ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getDichVu() {
        return dichVu;
    }

    public void setDichVu(String dichVu) {
        this.dichVu = dichVu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "id=" + id +
                ", tenKh='" + tenKh + '\'' +
                ", ngayNhan='" + ngayNhan + '\'' +
                ", ngayTra='" + ngayTra + '\'' +
                ", tenPhong='" + tenPhong + '\'' +
                ", dichVu='" + dichVu + '\'' +
                ", tongTien=" + tongTien +
                '}';
    }
}
