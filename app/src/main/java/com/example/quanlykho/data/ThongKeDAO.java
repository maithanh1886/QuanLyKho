package com.example.quanlykho.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykho.model.NhapKho;
import com.example.quanlykho.model.XuatKho;

import java.util.ArrayList;


public class ThongKeDAO {
    private final SQLiteDatabase sqLiteDatabase;
    private Context context;


    public ThongKeDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        sqLiteDatabase = helper.getWritableDatabase();

    }


    public ArrayList<XuatKho> XuatKhoByMonth(String thang) {
        String query = "SELECT Sp_id, SUM(phieuXk_soLuong) AS totalExport FROM tbl_phieuXk " +
                "WHERE substr(phieuXk_ngayXuat,6,2) = ? " +
                "GROUP BY Sp_id";
        ArrayList<XuatKho> list = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{ thang});
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    XuatKho xuatKho=new XuatKho();
                    xuatKho.setSp_Id(Integer.parseInt(c.getString(c.getColumnIndex("Sp_id"))));
                    xuatKho.setXuatKho(Integer.parseInt(c.getString(c.getColumnIndex("totalExport"))));
                    list.add(xuatKho);
                } while (c.moveToNext());
            }
            c.close();
        }
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<NhapKho> TonKhoByMonth(String thang) {
        String query = "SELECT Sp_id, SUM(phieuNk_soLuong) AS total FROM tbl_phieuNk " +
                "WHERE substr(phieuNk_ngayNhap,6,2) = ? " +
                "GROUP BY Sp_id";
        ArrayList<NhapKho> list = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{ thang});

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    NhapKho nhapKho =new NhapKho();
                    nhapKho.setSp_Id(Integer.parseInt(c.getString(c.getColumnIndex("Sp_id"))));
                    nhapKho.setTonKho(Integer.parseInt(c.getString(c.getColumnIndex("total"))));
                    list.add(nhapKho);
                } while (c.moveToNext());
            }
            c.close();
        }
        return list;
    }

    public ArrayList<XuatKho> XuatKhoByDate( String tuNgay,String denNgay) {

        String query = "SELECT Sp_id, SUM(phieuXk_soLuong) AS totalExport FROM tbl_phieuXk " +
                "WHERE phieuXk_ngayXuat BETWEEN ? AND ? " +
                "GROUP BY Sp_id";
        ArrayList<XuatKho> list = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{ tuNgay,denNgay});
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    XuatKho xuatKho=new XuatKho();
                    xuatKho.setSp_Id(Integer.parseInt(c.getString(c.getColumnIndex("Sp_id"))));
                    xuatKho.setXuatKho(Integer.parseInt(c.getString(c.getColumnIndex("totalExport"))));
                    list.add(xuatKho);
                } while (c.moveToNext());
            }
            c.close();
        }
        return list;
    }
    @SuppressLint("Range")
    public ArrayList<NhapKho> TonKhoByDate(String tuNgay,String denNgay) {
        String query = "SELECT Sp_id, SUM(phieuNk_soLuong) AS total FROM tbl_phieuNk " +
                "WHERE phieuNk_ngayNhap BETWEEN ? AND ? " +
                "GROUP BY Sp_id";
        ArrayList<NhapKho> list = new ArrayList<>();
        Cursor c = sqLiteDatabase.rawQuery(query, new String[]{ tuNgay,denNgay});

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    NhapKho nhapKho =new NhapKho();
                    nhapKho.setSp_Id(Integer.parseInt(c.getString(c.getColumnIndex("Sp_id"))));
                    nhapKho.setTonKho(Integer.parseInt(c.getString(c.getColumnIndex("total"))));
                    list.add(nhapKho);
                } while (c.moveToNext());
            }
            c.close();
        }
        return list;
    }
}
