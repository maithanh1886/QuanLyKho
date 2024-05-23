package com.example.quanlykho.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlykho.R;




public class DanhSach_Sp extends Fragment {

    public DanhSach_Sp() {
        // Required empty public constructor
    }


    public static DanhSach_Sp newInstance() {
        DanhSach_Sp fragment = new DanhSach_Sp();

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
        return inflater.inflate(R.layout.fragment_danh_sach__sp, container, false);
    }


}