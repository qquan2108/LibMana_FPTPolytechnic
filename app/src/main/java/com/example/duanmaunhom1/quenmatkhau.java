package com.example.duanmaunhom1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.duanmaunhom1.DAO.NguoiDungDAO;

public class quenmatkhau extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;
    EditText ettendangnhap,etsdt;
    Button btnxacnhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quenmatkhau);

        ettendangnhap=findViewById(R.id.ettendangnhap);
        etsdt=findViewById(R.id.etsdt);
        btnxacnhan=findViewById(R.id.btnlaimk);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thư viện Phương Nam");

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        nguoiDungDAO = new NguoiDungDAO(this);

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ettendangnhap.getText().toString().trim();
                String sdt = etsdt.getText().toString().trim();

                if (user.isEmpty() || sdt.isEmpty()) {
                    Toast.makeText(quenmatkhau.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean check = nguoiDungDAO.KiemTradmk(user, sdt);
                if (check) {
                    getSharedPreferences("dataUser", MODE_PRIVATE).edit()
                            .putString("tendangnhap", user)
                            .putString("sdt", sdt)
                            .apply();

                    Intent i = new Intent(quenmatkhau.this, nhapmkmoi.class);
                    startActivity(i);

                } else {
                    Toast.makeText(quenmatkhau.this, "Tên đăng nhập hoặc số điện thoại không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}