package com.example.duanmaunhom1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanmaunhom1.DAO.NguoiDungDAO;

public class nhapmkmoi extends AppCompatActivity {
    EditText etMatKhauMoi, etNhapLaiMatKhauMoi;
    Button btnXacNhanMkMoi;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhapmkmoi);

        etMatKhauMoi = findViewById(R.id.et_matkhau_moi);
        etNhapLaiMatKhauMoi = findViewById(R.id.et_nhaplai_matkhau_moi);
        btnXacNhanMkMoi = findViewById(R.id.btn_xacnhan_mk_moi);
        nguoiDungDAO = new NguoiDungDAO(this);

        btnXacNhanMkMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matKhauMoi = etMatKhauMoi.getText().toString().trim();
                String nhapLaiMatKhauMoi = etNhapLaiMatKhauMoi.getText().toString().trim();
                String tendangnhap = getSharedPreferences("dataUser", MODE_PRIVATE).getString("tendangnhap", null);

                if (matKhauMoi.isEmpty() || nhapLaiMatKhauMoi.isEmpty()) {
                    Toast.makeText(nhapmkmoi.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!matKhauMoi.equals(nhapLaiMatKhauMoi)) {
                    Toast.makeText(nhapmkmoi.this, "Mật khẩu mới và nhập lại mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tendangnhap != null) {
                    boolean isUpdated = nguoiDungDAO.capNhatMatKhauMoi(tendangnhap, matKhauMoi);
                    if (isUpdated) {
                        Toast.makeText(nhapmkmoi.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(nhapmkmoi.this, DangNhap.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(nhapmkmoi.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(nhapmkmoi.this, "Không tìm thấy tên đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
