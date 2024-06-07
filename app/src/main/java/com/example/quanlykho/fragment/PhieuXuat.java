package com.example.quanlykho.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.adapter.PhieuXuatAdapter;
import com.example.quanlykho.data.PhieuNkDAO;

import com.example.quanlykho.data.PhieuXkDAO;

import com.example.quanlykho.data.SanPhamDAO;
import com.example.quanlykho.model.PhieuXuatKho;
import com.example.quanlykho.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class PhieuXuat extends Fragment {

    private ArrayList<PhieuXuatKho> list;
    private ArrayList<SanPham> listSanPham;
    private PhieuXuatAdapter adapter;
    private ArrayAdapter<SanPham> adapterSanPham;

    PhieuXkDAO dao;
    //    SanPhamDAO sanPhamDAO;
    RecyclerView rvPhieuXuat;


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rvPhieuXuat = view.findViewById(R.id.rvDanhSachPhieuXuatKho);
        FloatingActionButton floatAdd = view.findViewById(R.id.Float_add_Phieu_xuat_kho);

        EditText edSearch = view.findViewById(R.id.edTimKiemPhieuSuatKho);
        ImageButton btnSearch = view.findViewById(R.id.btn_phieuxk_timkiem);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edSearch.length() > 0) {
                    String tentimkiem = edSearch.getText().toString();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    rvPhieuXuat.setLayoutManager(layoutManager);
                    PhieuXkDAO phieuXkDAO = new PhieuXkDAO(getContext());
                    list = new ArrayList<>();
                    list = phieuXkDAO.TimKiemPhXK(tentimkiem);
                    adapter.setData(list);
                    rvPhieuXuat.setAdapter(adapter);

                } else {
                    reloadData();
                }
            }
        });


        PhieuXkDAO phieuXkDAO = new PhieuXkDAO(getContext());
        list = phieuXkDAO.getAllData();
        adapter = new PhieuXuatAdapter(getContext(), list);


        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_phieu_xuat);
                dialog.setCancelable(false);

                Window window = dialog.getWindow();
                window.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText edTenSp, edSoLuong;
                Spinner spinnerSanPham;
                TextView tentv, tvNgayXuat;
                AppCompatButton btnThem, btnHuy;

                spinnerSanPham = dialog.findViewById(R.id.SpTenSpPhieuXuatThem);
//                edTenSp = dialog.findViewById(R.id.edTenSpPhieuXuatThem);
                edSoLuong = dialog.findViewById(R.id.edSoLuongSPPhieuXuatThem);
                tvNgayXuat = dialog.findViewById(R.id.tvNgayXuatPhieuXuatThem);
                btnThem = dialog.findViewById(R.id.btnThemPhieuXuat);
                btnHuy = dialog.findViewById(R.id.btnHuyLayouThemPhieuXuat);
                tentv = dialog.findViewById(R.id.dialogxuat_tennv);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("USERNAME", "");
                tentv.setText(username);
                SanPhamDAO sanPhamDAO = new SanPhamDAO(getContext());
                listSanPham = sanPhamDAO.getAllData();
                adapterSanPham = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listSanPham);
                spinnerSanPham.setAdapter(adapterSanPham);
                tvNgayXuat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar lich = Calendar.getInstance();
                        int year = lich.get(Calendar.YEAR);
                        int month = lich.get(Calendar.MONTH);
                        int day = lich.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datedg = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth);
                                String date = sdf.format(selectedCalendar.getTime());
                                tvNgayXuat.setText(date);
                            }
                        }, year, month, day);
                        datedg.show();
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (kiemTraThongTin()) {
                            SanPham selectedSanPham = (SanPham) spinnerSanPham.getSelectedItem();
                            if (selectedSanPham == null) {
                                Toast.makeText(getContext(), "Không có sản phẩm không thể xuất", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String ngayXuat = tvNgayXuat.getText().toString();
                            int soLuong = Integer.parseInt(edSoLuong.getText().toString());
                            if (soLuong <= 0) {
                                Toast.makeText(getContext(), "Số lượng sản phẩm phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            PhieuNkDAO nkDAO = new PhieuNkDAO(getContext());
                            int soLuongNhapHomTruoc = nkDAO.getSoLuongNhapHomTruoc(selectedSanPham.getId_sp(), ngayXuat);
                            int soLuongNhap= nkDAO.getSoLuongNhap(selectedSanPham.getId_sp());
                            int totalTon;
                            PhieuXkDAO xkDAO = new PhieuXkDAO(getContext());
                            int soLuongXuatHomTruoc = xkDAO.getSoLuongXuatHomTruoc(selectedSanPham.getId_sp(), ngayXuat);
                            int soluongXuat = xkDAO.getSoLuongXuat(selectedSanPham.getId_sp());
                            if (soluongXuat > 0) {
                                totalTon=soLuongNhap-soluongXuat;
                            } else {
                                totalTon = soLuongNhapHomTruoc;
                            }

                            if (totalTon <= 0) {
                                Toast.makeText(getContext(), "Không thể xuất vì tồn kho nhỏ hơn 0", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (soLuong > totalTon) {
                                Toast.makeText(getContext(), "Số lượng xuất không thể lớn hơn số lượng nhập!", Toast.LENGTH_SHORT).show();

                            } else {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                                String name = sharedPreferences.getString("USERNAME", "");

                                PhieuXuatKho phieuXuatKho = new PhieuXuatKho();
                                phieuXuatKho.setTentv(name);
                                phieuXuatKho.setId_sp(selectedSanPham.getId_sp());
                                phieuXuatKho.setNgayXuat(ngayXuat);
                                phieuXuatKho.setSoluong(soLuong);
                                long insertResult = xkDAO.insert(phieuXuatKho);
                                if (insertResult > 0) {
                                    Toast.makeText(getContext(), "Thêm thành công !", Toast.LENGTH_SHORT).show();
                                    list = xkDAO.getAllData();
                                    adapter.setData(list);
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    }


                    private boolean kiemTraThongTin() {
                        String ngayXuat = tvNgayXuat.getText().toString();
                        String soLuongStr = edSoLuong.getText().toString();

                        if (ngayXuat.equals("") || soLuongStr.equals("")) {
                            Toast.makeText(getContext(), "Mời nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        try {
                            int soLuong = Integer.parseInt(soLuongStr);
                            if (soLuong <= 0) {
                                Toast.makeText(getContext(), "Số lượng sản phẩm phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        } catch (NumberFormatException ex) {
                            Toast.makeText(getContext(), "Số lượng sản phẩm phải là số", Toast.LENGTH_SHORT).show();

                            return false;
                        }

                        return true;
                    }
                });
                dialog.show();
            }
        });

        reloadData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void reloadData() {
        dao = new PhieuXkDAO(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPhieuXuat.setLayoutManager(layoutManager);
        list = dao.getAllData();
        adapter.setData(list);
        rvPhieuXuat.setAdapter(adapter);
    }
}