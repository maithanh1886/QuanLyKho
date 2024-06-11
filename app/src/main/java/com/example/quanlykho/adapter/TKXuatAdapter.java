package com.example.quanlykho.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.data.SanPhamDAO;
import com.example.quanlykho.data.ThongKeDAO;
import com.example.quanlykho.model.NhapKho;
import com.example.quanlykho.model.SanPham;
import com.example.quanlykho.model.XuatKho;

import java.util.ArrayList;


public class TKXuatAdapter extends RecyclerView.Adapter<TKXuatAdapter.ViewHolder> {
    private ArrayList<XuatKho> list_xuat;
    private ArrayList<NhapKho> list_ton;
    Context context;

    private ThongKeDAO dao;
    SanPhamDAO sanPhamDAO;

    public TKXuatAdapter(ArrayList<XuatKho> list_xuat, ArrayList<NhapKho> list_ton, Context context) {
        this.list_xuat = list_xuat;
        this.list_ton = list_ton;
        this.context = context;
        sanPhamDAO=new SanPhamDAO(context);
    }

    public void setData(ArrayList<XuatKho> list_xuat, ArrayList<NhapKho> list_ton) {
        this.list_ton = list_ton;
        this.list_xuat = list_xuat;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statis, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NhapKho nhapKho = list_ton.get(position);
        XuatKho xuatKho = list_xuat.get(position);
        SanPham sanPham=sanPhamDAO.getByID1(String.valueOf(xuatKho.getSp_Id()));
        holder.tvIdSPl.setText((position+1)+". "+sanPham.getTen_sp() + "");
        String tonKho = "Tồn : "+(nhapKho.getTonKho() - xuatKho.getXuatKho()) + " sp";


        holder.tvTon.setText(tonKho);
        holder.tvXuat.setText("Xuất : "+ xuatKho.getXuatKho() + " sp");
    }

    @Override
    public int getItemCount() {
        if(list_xuat==null)
            return 0;
        return list_xuat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdSPl, tvTon, tvXuat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdSPl = itemView.findViewById(R.id.tv_id_sp);
            tvXuat = itemView.findViewById(R.id.tv_xuat);
            tvTon = itemView.findViewById(R.id.tv_ton);

        }
    }

}
