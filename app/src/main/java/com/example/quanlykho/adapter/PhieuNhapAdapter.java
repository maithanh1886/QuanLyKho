package com.example.quanlykho.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykho.R;
import com.example.quanlykho.data.PhieuNkDAO;
import com.example.quanlykho.data.SanPhamDAO;
import com.example.quanlykho.model.PhieuNhapKho;
import com.example.quanlykho.model.SanPham;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PhieuNhapAdapter extends RecyclerView.Adapter<PhieuNhapAdapter.ViewHolder> {

    Context myContext;
    ArrayList<PhieuNhapKho> list;
    ArrayList<SanPham> listSanPham;
    ArrayAdapter<SanPham> adapterSanPham;

    public PhieuNhapAdapter(Context myContext, ArrayList<PhieuNhapKho> list) {
        this.myContext = myContext;
        this.list = list;
    }

    public void setData(ArrayList<PhieuNhapKho> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) myContext).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieu_nhap_kho, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuNhapKho idPhieu = list.get(position);
        PhieuNkDAO phieuNkDAO = new PhieuNkDAO(myContext);

        holder.tentv.setText(list.get(position).getTentv() + "");
        holder.tvTenSp.setText(list.get(position).getId_sp() + "");
        holder.tvSoLuong.setText(list.get(position).getSoluong() + "");
        holder.tvNgayXuat.setText(list.get(position).getNgayNhap());

        //Sựa kiện xóa
        holder.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Thông báo ! ")
                        .setMessage("Bạn chắc xóa thông tin phiếu này không ?")
                        .setCancelable(true)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int kq = phieuNkDAO.delete(idPhieu.getId_pnk());
                                if (kq > 0) {
                                    Toast.makeText(myContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    list = phieuNkDAO.getAllData();
                                    setData(list);
                                } else {
                                    Toast.makeText(myContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        //Sựa kiên sửa
        holder.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_update_phieu_nhap);
                dialog.setCancelable(false);

                Window window = dialog.getWindow();
                window.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                );
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView tvNgayNhap;
                EditText edTen, edSoLuong;
                AppCompatButton btnSua, btnHuy;
                Spinner spinner;

                spinner = dialog.findViewById(R.id.SpTenSpPhieuNhapSua);
//                edTen = dialog.findViewById(R.id.edTenSpPhieuXuatSua);
                edSoLuong = dialog.findViewById(R.id.edSoLuongSPPhieuNhapSua);
                tvNgayNhap = dialog.findViewById(R.id.tvNgayXuatPhieuNhapSua);
                btnSua = dialog.findViewById(R.id.btnSuaPhieuNhap);
                btnHuy = dialog.findViewById(R.id.btnHuyLayouSuaPhieuNhap);

//                edTen.setText(idPhieu.getId_sp()+"");
                SanPhamDAO sanPhamDAO = new SanPhamDAO(myContext);
                listSanPham = sanPhamDAO.getAllData();
                adapterSanPham = new ArrayAdapter<>(myContext, android.R.layout.simple_list_item_1, listSanPham);
                spinner.setAdapter(adapterSanPham);
                int viTri = 0;
                for (int i = 0; i < listSanPham.size(); i++) {
                    if (idPhieu.getId_sp() == listSanPham.get(i).getId_sp()) {
                        viTri = i;
                        break;
                    }
                }
                spinner.setSelection(viTri);
                edSoLuong.setText(idPhieu.getSoluong() + "");
                tvNgayNhap.setText(idPhieu.getNgayNhap());

                tvNgayNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar lich= Calendar.getInstance();
                        int year=lich.get(Calendar.YEAR);
                        int month=lich.get(Calendar.MONTH);
                        int day=lich.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datedg=new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
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


                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = dateFormat.format(currentDate);
                tvNgayNhap.setText(formattedDate);
                ;

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (kiemTra()) {
                            SanPham sanPham = (SanPham) spinner.getSelectedItem();
                            int idSp = sanPham.getId_sp();
                            idPhieu.setId_sp(idSp);
                            idPhieu.setSoluong(Integer.parseInt(edSoLuong.getText().toString()));
                            idPhieu.setNgayNhap(tvNgayNhap.getText().toString());

                            int kq = phieuNkDAO.update(idPhieu);
                            if (kq > 0) {
                                Toast.makeText(myContext, "Sửa thành công ", Toast.LENGTH_SHORT).show();

                                list = phieuNkDAO.getAllData();
                                setData(list);
                                dialog.dismiss();

                            } else {
                                Toast.makeText(myContext, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    private boolean kiemTra() {

                        if (
                                tvNgayNhap.getText().toString().equals("")
                                        || edSoLuong.getText().toString().equals("")
                        ) {
                            Toast.makeText(myContext, "Mời nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        try {
                            Integer.parseInt(edSoLuong.getText().toString());
                        } catch (NumberFormatException ex) {
                            Toast.makeText(myContext, "Số lượng sản phẩm phải là số", Toast.LENGTH_SHORT).show();
                            return false;
                        }


                        if (Integer.parseInt(edSoLuong.getText().toString()) <= 0) {
                            Toast.makeText(myContext, "Số lượng sản phẩm phải lớn hơn 0 !", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        return true;
                    }
                });

                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTenSp, tvSoLuong, tvNgayXuat, tentv;
        ImageView ivXoa, ivSua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSp = itemView.findViewById(R.id.tvTenSanPhamPhieuNhap);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuongSanPhamPhieuNhap);
            tvNgayXuat = itemView.findViewById(R.id.tvNgayXuatSanPhamPhieuNhap);
            ivSua = itemView.findViewById(R.id.ivSuaSPPhieuNhap);
            ivXoa = itemView.findViewById(R.id.ivXoaSPPhieuNhap);
            tentv = itemView.findViewById(R.id.tentv);

        }
    }

}
