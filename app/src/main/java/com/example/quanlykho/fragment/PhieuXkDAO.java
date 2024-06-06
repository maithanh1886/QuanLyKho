package com.example.quanlykho.fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykho.data.DBHelper;
import com.example.quanlykho.model.PhieuXuatKho;

import java.util.ArrayList;


public class PhieuXkDAO {
    private final SQLiteDatabase sqLiteDatabase;
    private Context context;

    public PhieuXkDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public long insert(PhieuXuatKho ob) {
        ContentValues values = new ContentValues();
        values.put("Sp_id", ob.getId_sp());
        values.put("phieuXk_soLuong",ob.getSoluong());
        values.put("phieuXk_ngayXuat",ob.getNgayXuat());
        values.put("thanhVien_id",ob.getId_tv());
        values.put("thanhvien_hoten",ob.getTentv());
        return sqLiteDatabase.insert("tbl_phieuXk", null, values);
    }

    public int update(PhieuXuatKho ob) {
        ContentValues values = new ContentValues();
        values.put("Sp_id", ob.getId_sp());
        values.put("phieuXk_soLuong",ob.getSoluong());
        values.put("phieuXk_ngayXuat",ob.getNgayXuat());
        values.put("thanhVien_id",ob.getId_tv());
        return sqLiteDatabase.update("tbl_phieuXk", values, "phieuXk_id=?", new String[]{String.valueOf(ob.getId_pxk())});
    }


    public int delete(int ID) {
        return sqLiteDatabase.delete("tbl_phieuXk", "phieuXk_id=?", new String[]{String.valueOf(ID)});
    }

    @SuppressLint("Range")
    public ArrayList<PhieuXuatKho> getData(String sql, String... SelectAvgs) {
        ArrayList<PhieuXuatKho> lst = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            PhieuXuatKho ob=new PhieuXuatKho();
            ob.setId_pxk(Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuXk_id"))));
            ob.setId_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sp_id"))));
            ob.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuXk_soLuong"))));
            ob.setNgayXuat(cursor.getString(cursor.getColumnIndex("phieuXk_ngayXuat")));
            ob.setId_tv(Integer.parseInt(cursor.getString(cursor.getColumnIndex("thanhVien_id"))));
            ob.setTentv(cursor.getString(cursor.getColumnIndex("thanhVien_hoten")));
            lst.add(ob);
        }
        return lst;
    }

    public ArrayList<PhieuXuatKho> getAllData() {
        String sql = "SELECT * FROM tbl_phieuXk";
        return getData(sql);
    }

    public PhieuXuatKho getByID(String id) {
        String sql = "SELECT * FROM tbl_phieuXk  where phieuXk_id=?";
        return getData(sql, id).get(0);
    }
    public PhieuXuatKho getByID_SP(String id) {
        String sql = "SELECT * FROM tbl_phieuXk  where Sp_id=?";
        return getData(sql, id).get(0);
    }
    @SuppressLint("Range")
    public ArrayList<PhieuXuatKho> TimKiemPhXK(String ten) {
        ArrayList<PhieuXuatKho> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_phieuXk WHERE Sp_id LIKE '%"+ ten +"%' ", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                PhieuXuatKho ob=new PhieuXuatKho();
                ob.setId_pxk(Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuXk_id"))));
                ob.setId_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sp_id"))));
                ob.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuXk_soLuong"))));
                ob.setNgayXuat(cursor.getString(cursor.getColumnIndex("phieuXk_ngayXuat")));
                ob.setId_tv(Integer.parseInt(cursor.getString(cursor.getColumnIndex("thanhVien_id"))));
                ob.setTentv(cursor.getString(cursor.getColumnIndex("thanhVien_hoten")));
                list.add(ob);
            }
            while (cursor.moveToNext());
        }
        return list;

    }
    @SuppressLint("Range")
    public int getSoLuongXuatHomTruoc(int id_sp, String ngayXuat) {
        String sql = "SELECT SUM(phieuXk_soLuong) AS total FROM tbl_phieuXk WHERE Sp_id = ? AND phieuXk_ngayXuat <= ? ORDER BY phieuXk_ngayXuat";
        String[] selectionArgs = {String.valueOf(id_sp), ngayXuat};

        int soLuongXuat = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            soLuongXuat = cursor.getInt(cursor.getColumnIndex("total"));
        }

        cursor.close();
        return soLuongXuat;
    }
    public int getSoLuongXuat(int id_sp) {
        String sql = "SELECT SUM(phieuXk_soLuong) AS total FROM tbl_phieuXk WHERE Sp_id = ? ";
        String[] selectionArgs = {String.valueOf(id_sp)};

        int soLuongXuat = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            soLuongXuat = cursor.getInt(cursor.getColumnIndex("total"));
        }

        cursor.close();
        return soLuongXuat;
    }

  
}
