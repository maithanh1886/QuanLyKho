package com.example.quanlykho.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykho.model.ThanhVien;

import java.util.ArrayList;



public class ThanhVienDAO {
    private final SQLiteDatabase sqLiteDatabase;
    private Context context;
    DBHelper dbHelper;
    public ThanhVienDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public long insert(ThanhVien ob) {
        ContentValues values = new ContentValues();
        values.put("thanhVien_id", ob.getId_tv());
        values.put("thanhVien_hoTen", ob.getHoTen_tv());
        values.put("thanhVien_matKhau", ob.getMatKhau_tv());
        values.put("thanhVien_role", ob.getRole_tv());
        return sqLiteDatabase.insert("tbl_thanhVien", null, values);
    }

    public int update(ThanhVien ob) {
        ContentValues values = new ContentValues();
        values.put("thanhVien_id", ob.getId_tv());
        values.put("thanhVien_hoTen", ob.getHoTen_tv());
        values.put("thanhVien_matKhau", ob.getMatKhau_tv());
        values.put("thanhVien_role", ob.getRole_tv());
        return sqLiteDatabase.update("tbl_thanhVien", values, "thanhVien_id=?", new String[]{String.valueOf(ob.getId_tv())});
    }

    public int delete(int ID) {
        return sqLiteDatabase.delete("tbl_thanhVien", "thanhVien_id=?", new String[]{String.valueOf(ID)});
    }
    @SuppressLint("Range")
    public ArrayList<ThanhVien> getData(String sql, String... SelectAvgs) {
        ArrayList<ThanhVien> lst = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            ThanhVien ob = new ThanhVien();
            ob.setId_tv(cursor.getString(cursor.getColumnIndex("thanhVien_id")));
            ob.setHoTen_tv(cursor.getString(cursor.getColumnIndex("thanhVien_hoTen")));
            ob.setMatKhau_tv(cursor.getString(cursor.getColumnIndex("thanhVien_matKhau")));
            ob.setRole_tv(cursor.getString(cursor.getColumnIndex("thanhVien_role")));
            lst.add(ob);
        }
        return lst;
    }

    public ArrayList<ThanhVien> getAllData() {
        String sql = "SELECT * FROM tbl_thanhVien";
        return getData(sql);
    }

//    public ThuThu getByID(String id) {
//        String sql = "SELECT * FROM tbl_tt  where user_tt=?";
//        return getData(sql, id).get(0);
//    }
public String getRole(String  thanhVien_hoTen) {

    Cursor cursor = sqLiteDatabase.rawQuery("SELECT thanhVien_role FROM tbl_thanhVien WHERE thanhVien_hoTen = ?", new String[]{thanhVien_hoTen});
    if (cursor.getCount() > 0) {
        cursor.moveToFirst();
        return cursor.getString(0);
    } else return "Lỗi xác thực !";
}
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM tbl_thanhVien WHERE thanhVien_hoTen=? AND thanhVien_matKhau=?";
        ArrayList<ThanhVien> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }
//    public ArrayList<ThanhVien> getUsersByName(String thanhvien_hoTen) {
//        ArrayList<ThanhVien> userList = new ArrayList<>();
//
//        String[] columns = {"thanhVien_hoTen"};
//        String selection = "thanhVien_matKhau=?";
//        String[] selectionArgs = {thanhvien_hoTen};
//
//        Cursor cursor = sqLiteDatabase.query("tbl_thanhvien", columns, selection, selectionArgs, null, null, null);
//        while (cursor.moveToNext()) {
//
//            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("thanhVien_hoTen"));
//
//
//            ThanhVien user = new ThanhVien(name);
//            userList.add(user);
//        }
//        cursor.close();
//
//        return userList;
//    }
}
