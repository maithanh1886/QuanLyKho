package com.example.quanlykho.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import com.example.quanlykho.R;


public class Loai_Sp extends Fragment {



    public Loai_Sp() {
        // Required empty public constructor
    }

    public static Loai_Sp newInstance() {
        Loai_Sp fragment = new Loai_Sp();

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
        return inflater.inflate(R.layout.fragment_loai__sp, container, false);
    }
}