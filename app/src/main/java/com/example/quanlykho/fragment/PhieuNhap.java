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
import com.example.quanlykho.adapter.PhieuNhapAdapter;
import com.example.quanlykho.data.PhieuNkDAO;

import com.example.quanlykho.data.SanPhamDAO;
import com.example.quanlykho.model.PhieuNhapKho;
import com.example.quanlykho.model.SanPham;
import com.example.quanlykho.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;




public class PhieuNhap extends Fragment {


    private ArrayList<PhieuNhapKho> list;
    private ArrayList<SanPham> listSanPham;
    private PhieuNhapAdapter adapter;
    private ArrayAdapter<SanPham> adapterSanPham;
    PhieuNkDAO dao;
    ArrayList<ThanhVien> listUser;
    RecyclerView rvPhieuNhap;

    public PhieuNhap() {

    }


    public static PhieuNhap newInstance() {
        PhieuNhap fragment = new PhieuNhap();
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
        return inflater.inflate(R.layout.fragment_phieu_nhap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPhieuNhap = view.findViewById(R.id.rvDanhSachPhieuNhapKho);
        FloatingActionButton floatAdd = view.findViewById(R.id.Float_add_Phieu_nhap_kho);

        EditText edSearch = view.findViewById(R.id.edTimKiemPhieuNhapKho);
        ImageButton btnSearch = view.findViewById(R.id.btn_phieunk_timkiem);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edSearch.length() > 0) {
                    String tentimkiem = edSearch.getText().toString();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                    rvPhieuNhap.setLayoutManager(layoutManager);
                    PhieuXkDAO phieuXkDAO = new PhieuXkDAO(getContext());
                    list = new ArrayList<>();
                    list = dao.TimKiemPhNK(tentimkiem);
                    adapter.setData(list);
                    rvPhieuNhap.setAdapter(adapter);

                } else {
                    reloadData();
                }
            }
        });


        PhieuNkDAO phieuNkDAO = new PhieuNkDAO(getContext());
        list = phieuNkDAO.getAllData();
        adapter = new PhieuNhapAdapter(getContext(), list);


        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_phieu_nhap);
                dialog.setCancelable(false);

                Window window = dialog.getWindow();
                window.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                TextView tennv, tvNgayNhap;
                EditText edTenSp, edSoLuong;
                Spinner spinnerSanPham;
                AppCompatButton btnThem, btnHuy;

                spinnerSanPham = dialog.findViewById(R.id.SpTenSpPhieuNhapThem);
//                edTenSp = dialog.findViewById(R.id.edTenSpPhieuXuatThem);
                edSoLuong = dialog.findViewById(R.id.edSoLuongSPPhieuNhapThem);
                tvNgayNhap = dialog.findViewById(R.id.tvNgayXuatPhieuNhapThem);
                btnThem = dialog.findViewById(R.id.btnThemPhieuNhap);
                btnHuy = dialog.findViewById(R.id.btnHuyLayouThemPhieuNhap);
                tennv = dialog.findViewById(R.id.dialog_tennv);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("USERNAME", "");
                tennv.setText(username);

                SanPhamDAO sanPhamDAO = new SanPhamDAO(getContext());
                listSanPham = sanPhamDAO.getAllData();
                adapterSanPham = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listSanPham);
                spinnerSanPham.setAdapter(adapterSanPham);

                tvNgayNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar lich= Calendar.getInstance();
                        int year=lich.get(Calendar.YEAR);
                        int month=lich.get(Calendar.MONTH);
                        int day=lich.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datedg=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth);
                                String date = sdf.format(selectedCalendar.getTime());
                                tvNgayNhap.setText(date);
                            }
                        },year,month,day);
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
                        SanPham id = (SanPham) spinnerSanPham.getSelectedItem();
                        if (id == null) {
                            Toast.makeText(getContext(), "Không có sản phẩm không thể nhập", Toast.LENGTH_SHORT).show();
                        } else if (kiemTra()) {
//                            int tenSp = Integer.parseInt(edTenSp.getText().toString());
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                            String name = sharedPreferences.getString("USERNAME", "");
                            int tenSp = id.getId_sp();
                            int soLuong = Integer.parseInt(edSoLuong.getText().toString());
                            String ngayXuat = tvNgayNhap.getText().toString();

                            PhieuNhapKho phieuNhapKho = new PhieuNhapKho();
                            phieuNhapKho.setTentv(name);
                            phieuNhapKho.setId_sp(tenSp);
                            phieuNhapKho.setNgayNhap(ngayXuat);
                            phieuNhapKho.setSoluong(soLuong);

                            long kq = phieuNkDAO.insert(phieuNhapKho);
                            if (kq > 0) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                list = phieuNkDAO.getAllData();
                                adapter.setData(list);
                                SanPham sp = sanPhamDAO.getByID1(String.valueOf(phieuNhapKho.getId_sp()));
                                SanPham sanPham = new SanPham();
                                sanPham.setId_sp(sp.getId_sp());
//                                sanPhamDAO.updateSL(sanPham);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    private boolean kiemTra() {
                        if (
                                tvNgayNhap.getText().toString().equals("")
                                        || edSoLuong.getText().toString().equals("")
                        ) {
                            Toast.makeText(getContext(), "Mời nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        try {
                            Integer.parseInt(edSoLuong.getText().toString());
                        } catch (NumberFormatException ex) {
                            Toast.makeText(getContext(), "Số lượng sản phẩm phải là số", Toast.LENGTH_SHORT).show();
                            return false;
                        }


                        if (Integer.parseInt(edSoLuong.getText().toString()) <= 0) {
                            Toast.makeText(getContext(), "Số lượng sản phẩm phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        return true;
                    }
                });
                dialog.show();
            }
        });

        reloadData();
    }

    private void reloadData() {
        dao = new PhieuNkDAO(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPhieuNhap.setLayoutManager(layoutManager);
        list = dao.getAllData();
        adapter.setData(list);
        rvPhieuNhap.setAdapter(adapter);
    }
}