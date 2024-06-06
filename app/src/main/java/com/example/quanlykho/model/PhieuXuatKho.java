package com.example.quanlykho.model;

public class PhieuXuatKho {
    int id_pxk;
    int soluong;
    int id_sp;
    int id_tv;
    String ngayXuat,tentv;
    public int getId_tv() {
        return id_tv;
    }

    public String getTentv() {
        return tentv;
    }

    public void setTentv(String tentv) {
        this.tentv = tentv;
    }

    public void setId_tv(int id_tv) {
        this.id_tv = id_tv;
    }


    public int getId_pxk() {
        return id_pxk;
    }

    public void setId_pxk(int id_pxk) {
        this.id_pxk = id_pxk;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }
}
