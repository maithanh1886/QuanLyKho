package com.example.quanlykho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlykho.data.ThanhVienDAO;
import com.example.quanlykho.model.ThanhVien;

public class DangKyActivity extends AppCompatActivity {
    EditText edUsername_regis,edUserpass_regis,edUserRePass;
    Button btn_register;
    ThanhVienDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edUsername_regis=findViewById(R.id.edUsername_regis);
        edUserpass_regis=findViewById(R.id.edUserpass_regis);
        edUserRePass=findViewById(R.id.edUserRePass);
        btn_register = findViewById(R.id.btn_Register);

        dao=new ThanhVienDAO(this);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThanhVien user=new ThanhVien();
                user.setHoTen_tv(edUsername_regis.getText().toString());
                user.setMatKhau_tv(edUserpass_regis.getText().toString());
                user.setRole_tv("user");
                if(validate()>0){
                    if(dao.insert(user)>0){
                        Toast.makeText(DangKyActivity.this, "thêm tai khoan thanh cong", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(DangKyActivity.this, LoginActivity.class);
                        finish();
                        startActivity(i);

                        // Gửi broadcast thông báo sự thay đổi
                        Intent broadcastIntent = new Intent("ACTION_UPDATE_MEMBER_LIST");
                        DangKyActivity.this.sendBroadcast(broadcastIntent);
                        // Reset các EditText về giá trị rỗng
                        edUsername_regis.setText("");
                        edUserpass_regis.setText("");
                        edUserRePass.setText("");

                    }else {
                        Toast.makeText(DangKyActivity.this, "them that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public int validate(){
        int check=1;
        if(edUsername_regis.getText().length()==0||edUserpass_regis.getText().length()==0||edUserRePass.getText().length()==0
        ){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
            check=-1;
        }else{
            String pass=edUserpass_regis.getText().toString();
            String repass=edUserRePass.getText().toString();
            if(!pass.equals(repass)){
                Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check=-1;
            }else{
                check=1;
            }

        }
        return check;
    }
}