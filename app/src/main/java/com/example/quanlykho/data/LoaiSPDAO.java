package com.example.quanlykho.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykho.model.LoaiSP;

import java.util.ArrayList;


public class LoaiSPDAO {
    private final SQLiteDatabase sqLiteDatabase;
    private Context context;

    public LoaiSPDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public long insert(LoaiSP ob) {
        ContentValues values = new ContentValues();
        values.put("loaiSp_tenLoai", ob.getName_loaiSP());

        return sqLiteDatabase.insert("tbl_loaiSp", null, values);
    }

    public int update(LoaiSP ob) {
        ContentValues values = new ContentValues();
        values.put("loaiSp_tenLoai", ob.getName_loaiSP());
        return sqLiteDatabase.update("tbl_loaiSp", values, "loaiSp_id=?", new String[]{String.valueOf(ob.getId_loaiSP())});
    }

    public int delete(int ID) {
        return sqLiteDatabase.delete("tbl_loaiSp", "loaiSp_id=?", new String[]{String.valueOf(ID)});
    }
    @SuppressLint("Range")
    public ArrayList<LoaiSP> getData(String sql, String... SelectAvgs) {
        ArrayList<LoaiSP> lst = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            LoaiSP ob = new LoaiSP();
            ob.setId_loaiSP(Integer.parseInt(cursor.getString(cursor.getColumnIndex("loaiSp_id"))));
            ob.setName_loaiSP(cursor.getString(cursor.getColumnIndex("loaiSp_tenLoai")));
            lst.add(ob);
        }
        return lst;
    }

    public ArrayList<LoaiSP> getAllData() {
        String sql = "SELECT * FROM tbl_loaiSp";
        return getData(sql);
    }

    public LoaiSP getByID(String id) {
        String sql = "SELECT * FROM tbl_loaiSp  where loaiSp_id=?";
        return getData(sql, id).get(0);
    }
    @SuppressLint("Range")
    public ArrayList<String> getName(String sql,String...SelectAvgs){
        ArrayList<String> lst=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery(sql,SelectAvgs);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("loaiSp_tenLoai"));
            lst.add(name);
        }
        return lst;

    }
    public ArrayList<String> name(){
        String name="SELECT loaiSp_tenLoai FROM tbl_loaiSp";
        return getName(name);
    }
    @SuppressLint("Range")
    public ArrayList<LoaiSP> TimKiemLoaiSp(String ten) {
        ArrayList<LoaiSP> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbl_loaiSp WHERE loaiSp_tenLoai LIKE '%"+ ten +"%' ", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                LoaiSP loaiSP = new LoaiSP();
                loaiSP.setId_loaiSP(Integer.parseInt(cursor.getString(cursor.getColumnIndex("loaiSp_id"))));
                loaiSP.setName_loaiSP(cursor.getString(cursor.getColumnIndex("loaiSp_tenLoai")));
                list.add(loaiSP);

            }
            while (cursor.moveToNext());
        }
        return list;
    }
}
