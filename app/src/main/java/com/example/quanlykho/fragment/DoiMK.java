package com.example.quanlykho.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.quanlykho.R;
import com.example.quanlykho.data.ThanhVienDAO;
import com.example.quanlykho.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;



public class DoiMK extends Fragment {

    public DoiMK() {
        // Required empty public constructor
    }

    public static DoiMK newInstance() {
        DoiMK fragment = new DoiMK();

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
        return inflater.inflate(R.layout.fragment_doi_m_k, container, false);
    }

}