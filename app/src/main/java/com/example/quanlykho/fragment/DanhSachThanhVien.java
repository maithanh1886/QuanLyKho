package com.example.quanlykho.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.quanlykho.R;





public class DanhSachThanhVien extends Fragment {


    public DanhSachThanhVien() {
        // Required empty public constructor
    }


    public static DanhSachThanhVien newInstance() {
        DanhSachThanhVien fragment = new DanhSachThanhVien();
   ;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_thanh_vien, container, false);
    }



 
}