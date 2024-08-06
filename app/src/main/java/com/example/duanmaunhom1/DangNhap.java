package com.example.duanmaunhom1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanmaunhom1.DAO.NguoiDungDAO;
import com.example.duanmaunhom1.fragment.trangchufragment;

public class DangNhap extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;
    private Button bt0, btdangky, btLogin,btquenmatkhau;

    private EditText etUser, etPass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);


        etUser = findViewById(R.id.ettendangnhap);
        etPass = findViewById(R.id.etmatkhau);
        btLogin = findViewById(R.id.btdangnhap);
        btdangky = findViewById(R.id.btchuacotkdangky);
        btquenmatkhau = findViewById(R.id.btquenmatkhau);


        nguoiDungDAO = new NguoiDungDAO(this);

        btquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DangNhap.this,quenmatkhau.class);
                startActivity(i);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String pass = etPass.getText().toString();

                boolean check = nguoiDungDAO.KiemTraDangNhap(user, pass);
                if (check) {
                    int role = nguoiDungDAO.getRoleByUser(user);
                    // Cập nhật thông tin đăng nhập vào SharedPreferences
                    getSharedPreferences("dataUser", MODE_PRIVATE).edit()
                            .putString("tendangnhap", user)
                            .putInt("role", role)
                            .apply();

                    // Chuyển đến activity chính
                    Intent i = new Intent(DangNhap.this, trangchufragment.class);
                    startActivity(i);
                } else {
                    Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, Dangky.class);
                startActivity(intent);
            }
        });
    }
}
