package com.example.duanmaunhom1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.duanmaunhom1.DAO.NguoiDungDAO;

public class Dangky extends AppCompatActivity {
    EditText ettendangnhap, etmatkhau, etnhaplaimatkhau, etdiachi, etdienthoai, ettennguoidung;
    Button btdangky;
    private NguoiDungDAO nguoiDungDAO;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        ettendangnhap = findViewById(R.id.ettendangnhap);
        etmatkhau = findViewById(R.id.etpassword);
        etnhaplaimatkhau = findViewById(R.id.etnhaplaimatkhau);
        etdiachi = findViewById(R.id.etdiachi);
        etdienthoai = findViewById(R.id.etsdt);
        ettennguoidung = findViewById(R.id.ettennguoidung);
        btdangky = findViewById(R.id.btdangky);
        nguoiDungDAO = new NguoiDungDAO(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Đăng kí tài khoản");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ettendangnhap.getText().toString();
                String pass = etmatkhau.getText().toString();
                String repass = etnhaplaimatkhau.getText().toString();
                String diachi = etdiachi.getText().toString();
                String phone = etdienthoai.getText().toString();
                String name = ettennguoidung.getText().toString();

                if (user.isEmpty()) {
                    Toast.makeText(Dangky.this, "Chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(Dangky.this, "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (repass.isEmpty()) {
                    Toast.makeText(Dangky.this, "Chưa nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(repass)) {
                    Toast.makeText(Dangky.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(Dangky.this, "Chưa nhập tên người dùng", Toast.LENGTH_SHORT).show();
                } else if (phone.isEmpty()) {
                    Toast.makeText(Dangky.this, "Chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else if (diachi.isEmpty()) {
                    Toast.makeText(Dangky.this, "Chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else {
                    // Chuyển đến verify_phone để xác thực số điện thoại
                    Intent intent = new Intent(Dangky.this, verify_phone.class);
                    intent.putExtra("user", user);
                    intent.putExtra("pass", pass);
                    intent.putExtra("name", name);
                    intent.putExtra("diachi", diachi);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
