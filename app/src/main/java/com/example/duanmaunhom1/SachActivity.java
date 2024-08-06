package com.example.duanmaunhom1;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaunhom1.DAO.SachDAO;
import com.example.duanmaunhom1.adapter.SachAdapter;
import com.example.duanmaunhom1.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SachActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSach;
    private SachDAO sachDAO;
    private ArrayList<Sach> sachArrayList;
    private SachAdapter sachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sach);

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
        recyclerViewSach = findViewById(R.id.recyclerViewqlSach);
        FloatingActionButton floadd = findViewById(R.id.themmoi);
        floadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show dialog to add new book
                showdailogthem();
            }
        });

        // Initialize DAO and Adapter
        sachDAO = new SachDAO(this);
        sachArrayList = new ArrayList<>();
        sachAdapter = new SachAdapter(this, sachArrayList, sachDAO);

        // Setup RecyclerView
        recyclerViewSach.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSach.setAdapter(sachAdapter);

        // Load data
        loadSach();
    }

    private void loadSach() {
        sachArrayList.clear();
        sachArrayList.addAll(sachDAO.getDSSach());
        sachAdapter.notifyDataSetChanged();
    }

    private void showdailogthem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_sach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        // Bind UI elements
        EditText edtTenSach = view.findViewById(R.id.ettensach);
        EditText edTacGia = view.findViewById(R.id.ettacgia);
        EditText edtTienthue = view.findViewById(R.id.ettienthue);
        EditText edttheloai = view.findViewById(R.id.ettenloai);
        Button btnluu = view.findViewById(R.id.btluu);
        Button btnHuy = view.findViewById(R.id.bthuy);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                if (tenSach.equals("")) {
                    Toast.makeText(SachActivity.this, "Nhập tên sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tacGia = edTacGia.getText().toString();
                if (tacGia.equals("")) {
                    Toast.makeText(SachActivity.this, "Nhập tên tác giả", Toast.LENGTH_SHORT).show();
                    return;
                }
                int tienThue;
                int theLoai;
                try {
                    tienThue = Integer.parseInt(edtTienthue.getText().toString());
                    theLoai = Integer.parseInt(edttheloai.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(SachActivity.this, "Nhập giá thuê và thể loại hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean check = sachDAO.themSach(tenSach, tacGia, tienThue, theLoai);

                if (check) {
                    Toast.makeText(SachActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadSach();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(SachActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
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
        ArrayList<Sach> filteredList = new ArrayList<>();
        for (Sach sach : sachDAO.getDSSach()) {
            if (sach.getTensach().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(sach);
            }
        }
        sachArrayList.clear();
        sachArrayList.addAll(filteredList);
        sachAdapter.notifyDataSetChanged();
    }
}
