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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.data.LoaiSPDAO;
import com.example.quanlykho.model.LoaiSP;

import java.util.ArrayList;


public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.ViewHolder>{
    private ArrayList<LoaiSP> list;
    Context context;

    private LoaiSPDAO dao;

    public LoaiAdapter(Context context, ArrayList<LoaiSP> list, LoaiSPDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    public void setData(ArrayList<LoaiSP> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_name.setText(list.get(position).getName_loaiSP());
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDele(list.get(position).getId_loaiSP());
            }
        });
        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdate(list.get(position),position );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView sua,xoa;
        TextView tv_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tvTenLoaiSanPham);
            sua =itemView.findViewById(R.id.ivEditLoaiSp);
            xoa=itemView.findViewById(R.id.ivDeleteLoaiSp);
        }
    }
    public void showDele(int id){
        AlertDialog.Builder dialogDL = new AlertDialog.Builder(context);
        dialogDL.setMessage("Bạn có muốn xóa không?");
        dialogDL.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDL.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoaiSPDAO spdao = new LoaiSPDAO(context);
                if (spdao.delete(id) > 0) {
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    list = spdao.getAllData();
                    setData(list);
                } else {
                    Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();

            }
        });
        dialogDL.show();
    }
    public void showUpdate(LoaiSP loaiSP, int id){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update_loai_sp);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        EditText ed1;
        Button btnDialogAddCancel, btnDialogAddSubmit;
        ed1 = dialog.findViewById(R.id.edTenLoaiSPSua);

        ed1.setText(list.get(id).getName_loaiSP());

        btnDialogAddCancel = dialog.findViewById(R.id.btnHuyLayouSuaLoaiSp);
        btnDialogAddSubmit = dialog.findViewById(R.id.btnSuaLoaiSp);

        btnDialogAddCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDialogAddSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSPDAO loaiDao = new LoaiSPDAO(context);
                String ten = ed1.getText().toString();
                if (ten.trim().equals("")) {
                    Toast.makeText(context, "ko dc de trong", Toast.LENGTH_SHORT).show();
                }
                else {
                    loaiSP.setName_loaiSP(ed1.getText().toString());

                }
                if (loaiDao.update(loaiSP) > 0) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    list = loaiDao.getAllData();
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
