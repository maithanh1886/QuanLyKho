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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.adapter.LoaiAdapter;
import com.example.quanlykho.data.LoaiSPDAO;
import com.example.quanlykho.model.LoaiSP;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;



public class Loai_Sp extends Fragment {

    private LoaiSPDAO dao;
    RecyclerView recyclerView;
    private ArrayList<LoaiSP> list;
    private LoaiAdapter adapter;
    private EditText tim;
    ImageView search;

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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyLoaiSP = view.findViewById(R.id.rvLoaiSanPham);
        FloatingActionButton floatAdd = view.findViewById(R.id.Float_add_loaiSp);
        tim =view.findViewById(R.id.edTimKiemLoaiSP);
        search=view.findViewById(R.id.img_timkiem);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            list=dao.TimKiemLoaiSach(tim);
                if(tim.length()==0)
                {
                    Toast.makeText(getContext(), "không được để chống ô tim kiếm", Toast.LENGTH_SHORT).show();
                }
                else if (tim.length() > 0) {
                    String tentimkiem = tim.getText().toString();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    recyLoaiSP.setLayoutManager(layoutManager);
                    LoaiSPDAO loaiSachDAO1 = new LoaiSPDAO(getContext());
                    list = new ArrayList<>();
                    list = loaiSachDAO1.TimKiemLoaiSp(tentimkiem);
                    adapter.setData(list);
                    recyLoaiSP.setAdapter(adapter);

                } else {
                    capnhatdl();
                }


            }

        });
        LoaiSPDAO dao = new LoaiSPDAO(getContext());
        list = dao.getAllData();
        adapter = new LoaiAdapter(getContext(),list,dao);

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                LoaiSP ls = new LoaiSP();
                dialog.setContentView(R.layout.dialog_add_loai_san_pham);

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                EditText ed1;
                Button btnDialogAddCancel, btnDialogAddSubmit;
                ed1 = dialog.findViewById(R.id.edTenLoaiSPThem);

                btnDialogAddCancel = dialog.findViewById(R.id.btnHuyLayouThemLoaiSp);
                btnDialogAddSubmit = dialog.findViewById(R.id.btnThemLoaiSp);

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
                        if (ten.trim().equals("")) {
                            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                        }
                        else if (ten.length()<5) {
                            Toast.makeText(getContext(), "tối thiểu 5 ký tự", Toast.LENGTH_SHORT).show();
                        }
                        else if (ten.length()>20) {
                            Toast.makeText(getContext(), "tối đa 20 ký tự", Toast.LENGTH_SHORT).show();
                        }
//                        else if () {
//                            Toast.makeText(getContext(), "tối đa 20 ký tự", Toast.LENGTH_SHORT).show();
//                        }
                        else {
                            ls.setName_loaiSP(ed1.getText().toString());
                            if (dao.insert(ls) >= 0) {
                                Toast.makeText(getContext(), "them thanh cong", Toast.LENGTH_LONG).show();
                                list = dao.getAllData();
                                adapter.setData(list);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "them that bai!", Toast.LENGTH_LONG).show();
                            }
                        }
//                        if (dao.insert(ls) >= 0) {
//                            Toast.makeText(getContext(), "them thanh cong", Toast.LENGTH_LONG).show();
//                            list = dao.getAllData();
//                            adapter.setData(list);
//                            dialog.dismiss();
//                        } else {
//                            Toast.makeText(getContext(), "them that bai!", Toast.LENGTH_LONG).show();
//                        }
                    }
                });
                dialog.show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyLoaiSP.setLayoutManager(layoutManager);
        recyLoaiSP.setAdapter(adapter);

        super.onViewCreated(view, savedInstanceState);
    }
    private void capnhatdl() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        list = dao.getAllData();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
    }
}