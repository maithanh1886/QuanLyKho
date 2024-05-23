package com.example.quanlykho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykho.data.ThanhVienDAO;


public class LoginActivity extends AppCompatActivity {
    EditText edTenDN, edMatKhau;
    TextView tvDangKy;
    Button btnDangNhap;
    ThanhVienDAO dao;
    String strUser, strPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edTenDN=findViewById(R.id.edTenDN);
        edMatKhau=findViewById(R.id.edMatKhau);
        btnDangNhap=findViewById(R.id.btnDangNhap);
        tvDangKy = findViewById(R.id.tvDangKy);
        dao=new ThanhVienDAO(this);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DangKyActivity.class));
            }
        });


    }
    public void checkLogin() {
        strUser = edTenDN.getText().toString();
        strPass = edMatKhau.getText().toString();
        if (strUser.length() == 0) {
            edTenDN.requestFocus();
            edTenDN.setError("Vui lòng nhập tên đăng nhập");
        } else if (strPass.length() == 0) {
            edMatKhau.requestFocus();
            edMatKhau.setError("Vui lòng nhập mật khẩu");
        }else {
            if (dao.checkLogin(strUser, strPass) > 0) {
                Toast.makeText(this, "Login thanh cong", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                rememberUser(dao.getRole(strUser), strUser, strPass);
                finish();
            } else {
                Toast.makeText(this, "Login k thanh cong", Toast.LENGTH_SHORT).show();
            }

        }


    }
    public void rememberUser(String r, String u, String p ) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("USERNAME", u);
        edit.putString("PASSWORD", p);
        edit.putString("ROLE",r);
        edit.commit();
    }
}