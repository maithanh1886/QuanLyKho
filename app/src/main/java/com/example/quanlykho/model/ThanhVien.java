package com.example.quanlykho.model;

public class ThanhVien {
String id_tv;
String hoTen_tv,matKhau_tv,role_tv;

    public ThanhVien(String id_tv, String hoTen_tv, String matKhau_tv, String role_tv) {
        this.id_tv = id_tv;
        this.hoTen_tv = hoTen_tv;
        this.matKhau_tv = matKhau_tv;
        this.role_tv = role_tv;
    }
    public ThanhVien() {
    }
//    public ThanhVien(String user_name) {
//        this.id_tv = user_name;
//    }
    public String getId_tv() {
        return id_tv;
    }

    public void setId_tv(String id_tv) {
        this.id_tv = id_tv;
    }

    public String getHoTen_tv() {
        return hoTen_tv;
    }

    public void setHoTen_tv(String hoTen_tv) {
        this.hoTen_tv = hoTen_tv;
    }

    public String getMatKhau_tv() {
        return matKhau_tv;
    }

    public void setMatKhau_tv(String matKhau_tv) {
        this.matKhau_tv = matKhau_tv;
    }

    public String getRole_tv() {
        return role_tv;
    }

    public void setRole_tv(String role_tv) {
        this.role_tv = role_tv;
    }
}
