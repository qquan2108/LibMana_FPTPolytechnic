package com.example.duanmaunhom1;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaunhom1.DAO.ThanhVienDAO;
import com.example.duanmaunhom1.adapter.ThanhVienAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ThanhVienActivity extends AppCompatActivity {
    private RecyclerView recyclerViewqlthanhvien;
    private ThanhVienDAO thanhVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_vien);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thư viện LibMana");

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerViewqlthanhvien = findViewById(R.id.recyclerViewqlthanhvien);
        FloatingActionButton floadd = findViewById(R.id.themmoi);
        floadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdailog();
            }
        });

        thanhVienDAO = new ThanhVienDAO(this);
        loathanhvien();

    }
    private void loathanhvien() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewqlthanhvien.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(this, thanhVienDAO.getDSNguoidung(),thanhVienDAO);
        recyclerViewqlthanhvien.setAdapter(adapter);
    }
    public void showdailog( ){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_thanhvien,null);
        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        // anh xa
        EditText edtten = view.findViewById(R.id.ettennd);
        EditText etsdt = view.findViewById(R.id.etsdt);
        EditText etdiachi = view.findViewById(R.id.etdiachi);
        EditText ettendangnhap = view.findViewById(R.id.ettendangnhap);
        EditText etmatkhau = view.findViewById(R.id.etmatkhau);
        EditText etvaitro = view.findViewById(R.id.etvaitro);
        Button btluu = view.findViewById(R.id.btluu);
        Button bthuy = view.findViewById(R.id.bthuy);

        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tennd = edtten.getText().toString();
                if (tennd.equals("")){
                   Toast.makeText(ThanhVienActivity.this, "Nhap ten thanh vien", Toast.LENGTH_SHORT).show();
                   return;
                }
                String sdt = etsdt.getText().toString();
                if (sdt.equals("")){
                    Toast.makeText(ThanhVienActivity.this, "Nhap so dien thoai", Toast.LENGTH_SHORT).show();
                    return;
                }
                String diachi = etdiachi.getText().toString();
                if (diachi.equals("")){
                    Toast.makeText(ThanhVienActivity.this, "Nhap dia chi ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tendangnhap = ettendangnhap.getText().toString();
                if (tendangnhap.equals("")){
                    Toast.makeText(ThanhVienActivity.this, "Nhap ten dang nhap", Toast.LENGTH_SHORT).show();
                    return;
                }
                String matkhau = etmatkhau.getText().toString();
                if (matkhau.equals("")){
                    Toast.makeText(ThanhVienActivity.this, "nhap mat khau", Toast.LENGTH_SHORT).show();
                    return;
                }
                int vaitro = Integer.parseInt(etvaitro.getText().toString());


                boolean check = thanhVienDAO.themthanhvien(tennd,sdt,diachi,tendangnhap,matkhau,vaitro);

                if (check){
                    Toast.makeText(ThanhVienActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                    loathanhvien();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(ThanhVienActivity.this, "Them that bai", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }


}