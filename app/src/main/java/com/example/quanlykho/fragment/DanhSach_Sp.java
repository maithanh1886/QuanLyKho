package com.example.quanlykho.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.adapter.SanPhamAdapter;
import com.example.quanlykho.data.LoaiSPDAO;
import com.example.quanlykho.data.SanPhamDAO;
import com.example.quanlykho.model.LoaiSP;
import com.example.quanlykho.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DanhSach_Sp extends Fragment {
    private SanPhamDAO dao;
    private ArrayList<SanPham> list;
    private ArrayList<LoaiSP> llist;
    private SanPhamAdapter adapter;
    RecyclerView recy;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recy = view.findViewById(R.id.rvDanhSachSanPham);
        FloatingActionButton floatAdd = view.findViewById(R.id.Float_add_Sp);
        EditText edSearch = view.findViewById(R.id.ed_sanpham_timkiem);
        ImageButton btnSearch = view.findViewById(R.id.btn_sanpham_timkiem);

        SanPhamDAO spDao = new SanPhamDAO(getContext());
        LoaiSPDAO loaiDao = new LoaiSPDAO(getContext());
        list = spDao.getAllData();
        adapter = new SanPhamAdapter(list, getContext(), spDao);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edSearch.length() > 0) {
                    String tentimkiem = edSearch.getText().toString();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recy.setLayoutManager(layoutManager);
                    SanPhamDAO sanPhamDAO = new SanPhamDAO(getContext());
                    list = new ArrayList<>();
                    list = sanPhamDAO.TimKiemSp(tentimkiem);
                    adapter.setData(list);
                    recy.setAdapter(adapter);

                } else {
                    reloadData();
                }
            }
        });
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                SanPham s = new SanPham();
                dialog.setContentView(R.layout.dialog_add_san_pham);

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                EditText ed1, ed2, ed3, ed4;
                Spinner spinerSp;
                Button btnDialogAddCancel, btnDialogAddSubmit;
                ed1 = dialog.findViewById(R.id.ed_sanPham_add_name);
                ed2 = dialog.findViewById(R.id.ed_sanPham_add_price);


                spinerSp = dialog.findViewById(R.id.spn_sanPham_add_loaiSp);
                btnDialogAddCancel = dialog.findViewById(R.id.btn_sanPham_add_cancel);
                btnDialogAddSubmit = dialog.findViewById(R.id.btn_sanPham_add_submit);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, loaiDao.name());
                spinerSp.setAdapter(adapter1);
                spinerSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        llist = loaiDao.getAllData();
                        s.setLoai_Sp((llist.get(position).getName_loaiSP()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnDialogAddCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnDialogAddSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = ed1.getText().toString();
                        String giatien = ed2.getText().toString();

                        if (ten.trim().equals("") && giatien.trim().equals("")) {
                            Toast.makeText(getContext(), "ko dc de trong", Toast.LENGTH_SHORT).show();
                        } else {
                            s.setTen_sp(ed1.getText().toString());
                            s.setGia_sp(Integer.parseInt(ed2.getText().toString()));
                        }
                        if (spDao.insert(s) >= 0) {
                            Toast.makeText(getContext(), "them thanh cong", Toast.LENGTH_LONG).show();
                            list = spDao.getAllData();
                            adapter.setData(list);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "them that bai!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        reloadData();

        super.onViewCreated(view, savedInstanceState);
    }

    private void reloadData() {
        dao = new SanPhamDAO(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recy.setLayoutManager(layoutManager);
        list = dao.getAllData();
        adapter.setData(list);
        recy.setAdapter(adapter);

    }
}