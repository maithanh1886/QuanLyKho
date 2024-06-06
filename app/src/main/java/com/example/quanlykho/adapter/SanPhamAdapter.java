package com.example.quanlykho.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.data.LoaiSPDAO;
import com.example.quanlykho.data.SanPhamDAO;
import com.example.quanlykho.model.LoaiSP;
import com.example.quanlykho.model.SanPham;

import java.util.ArrayList;


public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private ArrayList<SanPham> list;
    Context context;

    private SanPhamDAO dao;


    public SanPhamAdapter(ArrayList<SanPham> list, Context context, SanPhamDAO dao) {
        this.list = list;
        this.context = context;
        this.dao = dao;
    }

    public void setData(ArrayList<SanPham> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_ten.setText(list.get(position).getTen_sp());
        holder.tv_giaTien.setText(String.valueOf(list.get(position).getGia_sp()));


        holder.tv_loaiSanPham.setText(String.valueOf(list.get(position).getLoai_Sp()));
        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdate(list.get(position), position);
            }
        });
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDele(list.get(position).getId_sp(), list.get(position).getTen_sp());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatButton sua,xoa;
        TextView tv_ten, tv_giaTien, tv_soLuong, tv_ngayNhap, tv_loaiSanPham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten = itemView.findViewById(R.id.tv_sanPham_tenSp);
            tv_giaTien = itemView.findViewById(R.id.tv_sanPham_giaTien);
            tv_loaiSanPham= itemView.findViewById(R.id.tv_sanPham_loaiSp);
            sua =itemView.findViewById(R.id.ivEditLoaiSp);
            xoa=itemView.findViewById(R.id.ivDeleteLoaiSp);
        }
    }
    public void showDele(int id,String tensp){
        AlertDialog.Builder dialogDL = new AlertDialog.Builder(context);
        dialogDL.setMessage("Bạn có muốn xóa sản phẩm: \"" + tensp + "\" không?");
        dialogDL.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDL.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao = new SanPhamDAO(context);
                if (dao.delete(id) > 0) {
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    list = dao.getAllData();
                    setData(list);
                } else {
                    Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();

            }
        });
        dialogDL.show();
    }
    public void showUpdate(SanPham sanPham, int id){
        Dialog dialog = new Dialog(context);
        LoaiSPDAO loaiDao = new LoaiSPDAO(context);
        dialog.setContentView(R.layout.dialog_update_san_pham);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        EditText ed1, ed2, ed3, ed4;
        Spinner spinerSp;
        Button btnDialogAddCancel, btnDialogAddSubmit;
        ed1 = dialog.findViewById(R.id.ed_sanPham_update_name);
        ed2 = dialog.findViewById(R.id.ed_sanPham_update_price);

        spinerSp = dialog.findViewById(R.id.spn_sanPham_update_loaiSp);


        ed1.setText(list.get(id).getTen_sp());
        ed2.setText(String.valueOf(list.get(id).getGia_sp()));


        btnDialogAddCancel = dialog.findViewById(R.id.btn_sanPham_update_cancel);
        btnDialogAddSubmit = dialog.findViewById(R.id.btn_sanPham_update_submit);

        ArrayList<LoaiSP> lsList = loaiDao.getAllData();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, loaiDao.name());
        spinerSp.setAdapter(adapter1);
        int spIndex = 0;
        for (LoaiSP ls : lsList) {
            if (ls.getName_loaiSP().equals(sanPham.getLoai_Sp())) {
                spinerSp.setSelection(spIndex);
                break;
            }
            spIndex++;
        }
        spinerSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String i = lsList.get(position).getName_loaiSP();
                sanPham.setLoai_Sp(i);
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
                SanPhamDAO spDao = new SanPhamDAO(context);
                String ten = ed1.getText().toString();
                String giatien = ed2.getText().toString();



                if (ten.trim().equals("") && giatien.trim().equals("")) {
                    Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
                else {
                    sanPham.setTen_sp(ed1.getText().toString());
                    sanPham.setGia_sp(Integer.parseInt(ed2.getText().toString()));

                }
                if (spDao.update(sanPham) > 0) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    list = spDao.getAllData();
                    setData(list);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }
}
