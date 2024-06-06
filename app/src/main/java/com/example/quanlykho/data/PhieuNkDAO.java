package com.example.quanlykho.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykho.model.PhieuNhapKho;

import java.util.ArrayList;


public class PhieuNkDAO {
    private final SQLiteDatabase sqLiteDatabase;
    private Context context;

    public PhieuNkDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public long insert(PhieuNhapKho ob) {
        ContentValues values = new ContentValues();
        values.put("Sp_id", ob.getId_sp());
        values.put("phieuNk_soLuong",ob.getSoluong());
        values.put("phieuNk_ngayNhap",ob.getNgayNhap());
        values.put("thanhvien_id",ob.getId_tv());
        values.put("thanhvien_hoten",ob.getTentv());
        return sqLiteDatabase.insert("tbl_phieuNk", null, values);
    }

    public int update(PhieuNhapKho ob) {
        ContentValues values = new ContentValues();
        values.put("Sp_id", ob.getId_sp());
        values.put("phieuNk_soLuong",ob.getSoluong());
        values.put("phieuNk_ngayNhap",ob.getNgayNhap());
        values.put("thanhVien_id",ob.getId_tv());
        values.put("thanhvien_hoten",ob.getTentv());
        return sqLiteDatabase.update("tbl_phieuNk", values, "phieuNk_id=?", new String[]{String.valueOf(ob.getId_pnk())});
    }


    public int delete(int ID) {
        return sqLiteDatabase.delete("tbl_phieuNk", "phieuNk_id=?", new String[]{String.valueOf(ID)});
    }

    @SuppressLint("Range")
    public ArrayList<PhieuNhapKho> getData(String sql, String... SelectAvgs) {
        ArrayList<PhieuNhapKho> lst = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            PhieuNhapKho ob=new PhieuNhapKho();
            ob.setId_pnk(Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuNk_id"))));
            ob.setId_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sp_id"))));
            ob.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuNk_soLuong"))));
            ob.setNgayNhap(cursor.getString(cursor.getColumnIndex("phieuNk_ngayNhap")));
            ob.setId_tv(Integer.parseInt(cursor.getString(cursor.getColumnIndex("thanhVien_id"))));
            ob.setTentv(cursor.getString(cursor.getColumnIndex("thanhVien_hoten")));
            lst.add(ob);
        }
        return lst;
    }

    public ArrayList<PhieuNhapKho> getAllData() {
        String sql = "SELECT * FROM tbl_phieuNk";
        return getData(sql);
    }

    public PhieuNhapKho getByID(String id) {
        String sql = "SELECT * FROM tbl_phieuNk  where phieuNk_id=?";
        return getData(sql, id).get(0);
    }

    @SuppressLint("Range")
    public ArrayList<PhieuNhapKho> TimKiemPhNK(String ten) {
        ArrayList<PhieuNhapKho> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_phieuNk WHERE Sp_id LIKE '%"+ ten +"%' ", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                PhieuNhapKho ob=new PhieuNhapKho();
                ob.setId_pnk(Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuNk_id"))));
                ob.setId_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sp_id"))));
                ob.setSoluong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuNk_soLuong"))));
                ob.setNgayNhap(cursor.getString(cursor.getColumnIndex("phieuNk_ngayNhap")));
                ob.setId_tv(Integer.parseInt(cursor.getString(cursor.getColumnIndex("thanhVien_id"))));
                ob.setTentv(cursor.getString(cursor.getColumnIndex("thanhVien_hoten")));
                list.add(ob);
            }
            while (cursor.moveToNext());
        }
        return list;

    }
    @SuppressLint("Range")
    public int CheckSoLuong() {
        int total = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT phieuNk_soLuong FROM tbl_phieuNk WHERE phieuNk_soLuong ", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndex("phieuNk_soLuong")));
                total += quantity;
            }
            while (cursor.moveToNext());
        }
        return total;

    }
    @SuppressLint("Range")
    public int getSoLuongNhapHomTruoc(int id_sp, String ngayXuat) {
        String sql = "SELECT SUM(phieuNk_soLuong) AS total FROM tbl_phieuNk WHERE Sp_id = ? AND phieuNk_ngayNhap <= ? ORDER BY phieuNk_ngayNhap";
        String[] selectionArgs = {String.valueOf(id_sp), ngayXuat};

        int soLuongNhap = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            soLuongNhap = cursor.getInt(cursor.getColumnIndex("total"));
        }

        cursor.close();
        return soLuongNhap;
    }
    @SuppressLint("Range")
    public int getSoLuongNhap(int id_sp) {
        String sql = "SELECT SUM(phieuNk_soLuong) AS total FROM tbl_phieuNk WHERE Sp_id = ? ";
        String[] selectionArgs = {String.valueOf(id_sp)};

        int soLuongNhap = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        if (cursor.moveToFirst()) {
            soLuongNhap = cursor.getInt(cursor.getColumnIndex("total"));
        }

        cursor.close();
        return soLuongNhap;
    }




  
}
