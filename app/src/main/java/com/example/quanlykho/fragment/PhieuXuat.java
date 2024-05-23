package com.example.quanlykho.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import com.example.quanlykho.R;




public class PhieuXuat extends Fragment {




    public PhieuXuat() {
        // Required empty public constructor
    }


    public static PhieuXuat newInstance() {
        PhieuXuat fragment = new PhieuXuat();

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
        return inflater.inflate(R.layout.fragment_phieu_xuat, container, false);
    }


}