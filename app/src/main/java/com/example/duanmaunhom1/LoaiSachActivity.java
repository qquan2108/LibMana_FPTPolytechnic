package com.example.duanmaunhom1;

import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaunhom1.DAO.LoaiSachDAO;
import com.example.duanmaunhom1.adapter.LoaiSachAdapter;
import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LoaiSachActivity extends AppCompatActivity {
    private LoaiSachDAO loaiSachDAO;
    private ArrayList<LoaiSach> list;
    private ArrayList<LoaiSach> dstimkiem;
    private RecyclerView recyclerViewLoaiSach;
    private RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loai_sach2);

        // Setup Toolbar
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

        // Initialize RecyclerView and FloatingActionButton
        recyclerViewLoaiSach = findViewById(R.id.recyclerViewqlloaisach);
        FloatingActionButton floatadd = findViewById(R.id.themmoi);
        main = findViewById(R.id.main);

        // Initialize DAO and lists
        loaiSachDAO = new LoaiSachDAO(this);
        list = new ArrayList<>();
        dstimkiem = new ArrayList<>();

        // Setup FloatingActionButton
        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogthem();
            }
        });

        // Load data into RecyclerView
        capnhapdulieu();
    }

    private void capnhapdulieu() {
        list = loaiSachDAO.getDSLoaiSach();
        dstimkiem.clear();
        dstimkiem.addAll(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewLoaiSach.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(this, dstimkiem, loaiSachDAO);
        recyclerViewLoaiSach.setAdapter(loaiSachAdapter);
    }

    private void showDialogthem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_loaisach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        // Bind UI elements
        EditText edttenloaisach = view.findViewById(R.id.ettenloai);
        Button btnluu = view.findViewById(R.id.btluu);
        Button btnHuy = view.findViewById(R.id.bthuy);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edttenloaisach.getText().toString();
                if (tenLoai.equals("")) {
                    Toast.makeText(LoaiSachActivity.this, "Nhập đủ dữ liệu !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean check = loaiSachDAO.themloaisach(tenLoai);
                if (check) {
                    shownote(main, "Thêm Thành công");
                    capnhapdulieu();
                    alertDialog.dismiss();
                } else {
                    shownote(main, "Thêm Thất Bại");
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void shownote(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action when "OK" is pressed
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchDatabase(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchDatabase(newText);
                return false;
            }
        });

        return true;
    }

    private void searchDatabase(String query) {
        dstimkiem.clear();
        for (LoaiSach loaiSach : list) {
            if (loaiSach.getTenloai().toLowerCase().contains(query.toLowerCase())) {
                dstimkiem.add(loaiSach);
            }
        }
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(this, dstimkiem, loaiSachDAO);
        recyclerViewLoaiSach.setAdapter(loaiSachAdapter);
    }
}
