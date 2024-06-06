package com.example.quanlykho.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykho.model.SanPham;

import java.util.ArrayList;


public class SanPhamDAO {
    private final SQLiteDatabase sqLiteDatabase;
    private Context context;

    public SanPhamDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public long insert(SanPham ob) {
        ContentValues values = new ContentValues();
        values.put("Sp_tenSp", ob.getTen_sp());
        values.put("loaiSp_tenLoai",ob.getLoai_Sp());
        values.put("Sp_giaTien",ob.getGia_sp());
        return sqLiteDatabase.insert("tbl_Sp", null, values);
    }

    public int update(SanPham ob) {
        ContentValues values = new ContentValues();
        values.put("Sp_tenSp", ob.getTen_sp());
        values.put("loaiSp_tenLoai",ob.getLoai_Sp());
        values.put("Sp_giaTien",ob.getGia_sp());
        return sqLiteDatabase.update("tbl_Sp", values, "Sp_id=?", new String[]{String.valueOf(ob.getId_sp())});
    }
//    public int updateSL(SanPham ob) {
//        ContentValues values = new ContentValues();
//        values.put("Sp_giaTien",ob.getGia_sp());
//        return sqLiteDatabase.update("tbl_Sp", values, "Sp_id=?", new String[]{String.valueOf(ob.getId_sp())});
//    }


    public int delete(int ID) {
        return sqLiteDatabase.delete("tbl_Sp", "Sp_id=?", new String[]{String.valueOf(ID)});
    }

    @SuppressLint("Range")
    public ArrayList<SanPham> getData(String sql, String... SelectAvgs) {
        ArrayList<SanPham> lst = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            SanPham ob = new SanPham();
            ob.setId_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sp_id"))));
            ob.setGia_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sp_giaTien"))));
            ob.setLoai_Sp(cursor.getString(cursor.getColumnIndex("loaiSp_tenLoai")));
            ob.setTen_sp(cursor.getString(cursor.getColumnIndex("Sp_tenSp")));
            lst.add(ob);
        }
        return lst;
    }

    public ArrayList<SanPham> getAllData() {
        String sql = "SELECT * FROM tbl_Sp";
        return getData(sql);
    }

    public SanPham getByID(String id) {
        String sql = "SELECT * FROM tbl_Sp  where loaiSp_tenLoai=?";
        return getData(sql, id).get(0);
    }
    public SanPham getByID1(String id) {
        String sql = "SELECT * FROM tbl_Sp  where Sp_id=?";
        return getData(sql, id).get(0);
    }
    public ArrayList<String> getName(String sql,String...SelectAvgs){
        ArrayList<String> lst=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery(sql,SelectAvgs);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("Sp_tenSp"));
            lst.add(name);
        }
        return lst;

    }
    public ArrayList<String> name(){
        String name="SELECT Sp_tenSp FROM tbl_Sp";
        return getName(name);
    }
    @SuppressLint("Range")
    public ArrayList<SanPham> TimKiemSp(String ten) {
        ArrayList<SanPham> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_Sp WHERE Sp_tenSp LIKE '%"+ ten +"%' ", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                SanPham ob = new SanPham();
                ob.setId_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sp_id"))));
                ob.setGia_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Sp_giaTien"))));
                ob.setLoai_Sp(cursor.getString(cursor.getColumnIndex("loaiSp_tenLoai")));
                ob.setTen_sp(cursor.getString(cursor.getColumnIndex("Sp_tenSp")));
                list.add(ob);

            }
            while (cursor.moveToNext());
        }
        return list;
    }
}
