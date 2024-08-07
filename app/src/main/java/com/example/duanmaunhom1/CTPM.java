package com.example.duanmaunhom1;

import android.content.Context;
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

import com.example.duanmaunhom1.DAO.ChiTietpmDAO;
import com.example.duanmaunhom1.adapter.CTPMAdapter;
import com.example.duanmaunhom1.model.CTPhieuMuon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CTPM extends AppCompatActivity {
    private RecyclerView recyclerCTPM;
    private ChiTietpmDAO CTPMDAO;
    private List<CTPhieuMuon> filteredList = new ArrayList<>(); // Danh sách để lưu các phiếu mượn đã lọc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ctpm);

        // Thiết lập toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thư viện LibMana");

        getSupportActionBar().setDisplayUseLogoEnabled(true);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay lại khi nhấn vào nút điều hướng
            }
        });

        recyclerCTPM = findViewById(R.id.recyclerCTPM);
        FloatingActionButton floatadd = findViewById(R.id.themmoi);
        FloatingActionButton filterReturned = findViewById(R.id.filterReturned); // FloatingActionButton để lọc các phiếu mượn đã trả
        CTPMDAO = new ChiTietpmDAO(this);
        loadCTPM(CTPMDAO.getCTPM()); // Tải danh sách phiếu mượn

        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdailogctpt(); // Hiển thị dialog thêm phiếu mượn mới
            }
        });

        filterReturned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListByReturned(); // Lọc danh sách các phiếu mượn đã trả
            }
        });
    }

    // Hàm để tải danh sách phiếu mượn lên RecyclerView
    private void loadCTPM(List<CTPhieuMuon> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerCTPM.setLayoutManager(linearLayoutManager);
        CTPMAdapter adapter = new CTPMAdapter((Context) this, (ArrayList<CTPhieuMuon>) list, CTPMDAO);
        recyclerCTPM.setAdapter(adapter);
    }

    // Hàm để lọc các phiếu mượn đã trả
    private void filterListByReturned() {
        filteredList.clear(); // Xóa danh sách lọc hiện tại
        for (CTPhieuMuon chiTietPhieuMuon : CTPMDAO.getCTPM()) {
            if (chiTietPhieuMuon.getTrasach()== 0) { // Kiểm tra trạng thái trả sách
                filteredList.add(chiTietPhieuMuon); // Thêm phiếu mượn đã trả vào danh sách lọc
            }
        }
        loadCTPM(filteredList); // Cập nhật RecyclerView với danh sách đã lọc
    }

    // Hàm để hiển thị dialog thêm phiếu mượn mới
    public void showdailogctpt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_ctpm, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        EditText etmapm = view.findViewById(R.id.etmapm);
        EditText etmasach = view.findViewById(R.id.etmasach);
        EditText etsoluong = view.findViewById(R.id.etsoluong);
        Button btnluu = view.findViewById(R.id.btluu);
        Button btnhuy = view.findViewById(R.id.bthuy);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mapm = Integer.parseInt(etmapm.getText().toString());
                int masach = Integer.parseInt(etmasach.getText().toString());
                int soluong = Integer.parseInt(etsoluong.getText().toString());

                // Kiểm tra và thêm chi tiết phiếu mượn
                boolean check = CTPMDAO.themctpm(mapm, masach, soluong);

                if (check) {
                    Toast.makeText(CTPM.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadCTPM(CTPMDAO.getCTPM()); // Tải lại danh sách phiếu mượn
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(CTPM.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss(); // Đóng dialog
            }
        });
    }
}
