package com.example.quanlykho.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.adapter.TKXuatAdapter;
import com.example.quanlykho.data.ThongKeDAO;
import com.example.quanlykho.model.NhapKho;
import com.example.quanlykho.model.XuatKho;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class ThongKe extends Fragment {
    private ArrayList<XuatKho> list_xuat;
    private ArrayList<NhapKho> list_ton;
    private ThongKeDAO dao;
    TKXuatAdapter adapter;
    RecyclerView recy;
    Button btn_tuNgay, btn_denNgay, btn_thongKe;
    TextView tv_tuNgay, tv_denNgay;
    private final Calendar myCalendar = Calendar.getInstance();

    public ThongKe() {
        // Required empty public constructor
    }


    public static ThongKe newInstance() {
        ThongKe fragment = new ThongKe();
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
        return inflater.inflate(R.layout.fragment_t_k_ton, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner sp_month = view.findViewById(R.id.sp_month);
        recy = view.findViewById(R.id.recy_statis);
        dao = new ThongKeDAO(getContext());
        RecyclerView rc = view.findViewById(R.id.recy_statis);
        adapter = new TKXuatAdapter(list_xuat, list_ton, getContext());

        PieChart pieChart = view.findViewById(R.id.pieChart);

        btn_tuNgay = view.findViewById(R.id.btn_tuNgay);
        btn_denNgay = view.findViewById(R.id.btn_denNgay);
        btn_thongKe = view.findViewById(R.id.btn_thongKe);
        tv_tuNgay = view.findViewById(R.id.tv_tuNgay);
        tv_denNgay = view.findViewById(R.id.tv_denNgay);
        TextView tv_null = view.findViewById(R.id.tv_null);
        tv_null.setVisibility(View.INVISIBLE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc.setLayoutManager(linearLayoutManager);
        recy.setAdapter(adapter);
        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv_tuNgay.setText("");
                tv_denNgay.setText("");
                String m;
                if (i < 9) {
                    m = "0" + "" + (i + 1);
                } else {
                    m = "" + (i + 1);
                }
                list_xuat = dao.XuatKhoByMonth(m);
                list_ton = dao.TonKhoByMonth(m);
                adapter.setData(list_xuat, list_ton);
                recy.setAdapter(adapter);
                if (list_ton.size() == 0 && list_xuat.size() == 0) {
                    tv_null.setVisibility(View.VISIBLE);

                    pieChart.setVisibility(View.INVISIBLE);
                } else {
                    tv_null.setVisibility(View.INVISIBLE);
                    pieChart.setVisibility(View.VISIBLE);

                    int xuat = 0;
                    for (XuatKho xuatKho : list_xuat) {
                        xuat += xuatKho.getXuatKho();
                    }
                    int nhap = 0;
                    for (NhapKho nhapKho : list_ton) {
                        nhap += nhapKho.getTonKho();
                    }



                    ArrayList<PieEntry> entries = new ArrayList<>();
                    entries.add(new PieEntry(xuat, "Xuất"));
                    entries.add(new PieEntry(nhap - xuat, "Tồn"));

                    PieDataSet dataSet = new PieDataSet(entries, " ");
                    ArrayList<Integer> colors = new ArrayList<>();
                    colors.add(getResources().getColor(R.color.blue)); // Màu sắc cho thu nhập
                    colors.add(getResources().getColor(R.color.red));
                    dataSet.setColors(colors);
                    dataSet.setValueTextSize(20);
                    PieData data = new PieData(dataSet);
                    pieChart.setData(data);
                    pieChart.animateY(1000);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_tuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date selectedDate = myCalendar.getTime();
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        tv_tuNgay.setText(dateFormatter.format(selectedDate));

                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btn_denNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date selectedDate = myCalendar.getTime();
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        tv_denNgay.setText(dateFormatter.format(selectedDate));

                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btn_thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuNgay = tv_tuNgay.getText().toString();
                String denNgay = tv_denNgay.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = sdf.parse(tuNgay);
                    Date date2 = sdf.parse(denNgay);
                    int compare = date2.compareTo(date1);
                    if (compare < 0) {
                        Toast.makeText(getContext(), "Chọn ngày thống kê không hợp lệ", Toast.LENGTH_SHORT).show();
                        tv_tuNgay.setText("");
                        tv_denNgay.setText("");
                    }
                } catch (Exception e) {

                }

                if (tuNgay.equals("") || denNgay.equals("")) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày!", Toast.LENGTH_SHORT).show();
                } else {
                    list_xuat = dao.XuatKhoByDate(tuNgay, denNgay);
                    list_ton = dao.TonKhoByDate(tuNgay, denNgay);
                    adapter.setData(list_xuat, list_ton);
                    recy.setAdapter(adapter);
                    if (list_ton.size() == 0 && list_xuat.size() == 0) {
                        tv_null.setVisibility(View.VISIBLE);

                        pieChart.setVisibility(View.INVISIBLE);

                    } else {
                        tv_null.setVisibility(View.INVISIBLE);
                        int xuat = 0;
                        for (XuatKho xuatKho : list_xuat) {
                            xuat += xuatKho.getXuatKho();
                        }
                        int nhap = 0;
                        for (NhapKho nhapKho : list_ton) {
                            nhap += nhapKho.getTonKho();
                        }


                        pieChart.setVisibility(View.VISIBLE);
                        ArrayList<PieEntry> entries = new ArrayList<>();
                        entries.add(new PieEntry(xuat, "Xuất"));
                        entries.add(new PieEntry(nhap - xuat, "Tồn"));

                        PieDataSet dataSet = new PieDataSet(entries, " ");
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(getResources().getColor(R.color.blue)); // Màu sắc cho thu nhập
                        colors.add(getResources().getColor(R.color.red));
                        dataSet.setColors(colors);
                        dataSet.setValueTextSize(20);
                        PieData data = new PieData(dataSet);
                        pieChart.setData(data);
                        pieChart.animateY(1000);

                    }
                }


            }
        });
    }


}