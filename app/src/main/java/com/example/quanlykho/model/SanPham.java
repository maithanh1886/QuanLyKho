package com.example.quanlykho.model;

public class SanPham {
    int id_sp,gia_sp;
    String ten_sp, loai_Sp;

    public SanPham() {
    }

    public SanPham(int id_sp, int gia_sp, String ten_sp, String loai_Sp) {
        this.id_sp = id_sp;
        this.gia_sp = gia_sp;
        this.ten_sp = ten_sp;
        this.loai_Sp = loai_Sp;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }


    public int getGia_sp() {
        return gia_sp;
    }

    public void setGia_sp(int gia_sp) {
        this.gia_sp = gia_sp;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }


    public String getLoai_Sp() {
        return loai_Sp;
    }

    public void setLoai_Sp(String loai_Sp) {
        this.loai_Sp = loai_Sp;
    }

    public String toString(){

        return this.id_sp +" : "+this.ten_sp;
    }

}
