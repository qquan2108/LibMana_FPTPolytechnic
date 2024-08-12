package com.example.duanmaunhom1;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.example.duanmaunhom1.DAO.PhieuMuonDAO;
import com.example.duanmaunhom1.adapter.PhieuMuonAdapter;
import com.example.duanmaunhom1.model.PhieuMuon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PhieuMuonMainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewphieumuon; // RecyclerView để hiển thị danh sách phiếu mượn
    private PhieuMuonDAO phieuMuonDAO; // DAO để tương tác với cơ sở dữ liệu
    private ArrayList<PhieuMuon> list; // Danh sách tất cả phiếu mượn
    private ArrayList<PhieuMuon> filteredList; // Danh sách phiếu mượn đã được lọc/sắp xếp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Kích hoạt chế độ cạnh-to-cạnh
        setContentView(R.layout.activity_phieu_muon_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thư viện Phương Nam");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        toolbar.setNavigationIcon(R.drawable.back); // Đặt biểu tượng điều hướng
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay lại trang trước khi nhấn vào biểu tượng điều hướng
            }
        });

        recyclerViewphieumuon = findViewById(R.id.recyclerViewqlpm); // Liên kết RecyclerView
        FloatingActionButton floadd = findViewById(R.id.themmoi); // Liên kết nút thêm mới phiếu mượn
        phieuMuonDAO = new PhieuMuonDAO(this); // Khởi tạo DAO

        list = phieuMuonDAO.getDSPhieuMuon(); // Lấy danh sách phiếu mượn từ DAO
        filteredList = new ArrayList<>(list); // Khởi tạo danh sách lọc ban đầu

        floadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogpm(); // Hiển thị dialog thêm mới phiếu mượn
            }
        });

        loadPhieuMuon(); // Tải danh sách phiếu mượn lên giao diện
    }

    private void loadPhieuMuon() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this); // Thiết lập layout manager cho RecyclerView
        recyclerViewphieumuon.setLayoutManager(linearLayoutManager); // Áp dụng layout manager
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(this, filteredList, phieuMuonDAO); // Tạo adapter với danh sách đã lọc
        recyclerViewphieumuon.setAdapter(adapter); // Áp dụng adapter cho RecyclerView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ltkpl, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search); // ID phải trùng với ID trong tệp XML menu
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) { // Kiểm tra nếu searchView không phải là null
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
        } else {
            // Xử lý trường hợp searchView là null
            Log.e("PhieuMuonMainActivity", "SearchView is null.");
        }

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.sort_by_name) {
            sortListByName(); // Sắp xếp danh sách theo tên
            return true;
        } else if (itemId == R.id.sort_by_date) {
            sortListByDate(); // Sắp xếp danh sách theo ngày
            return true;
        } else if (itemId == R.id.filter_by_borrowed) {
            filterListByBorrowed(); // Lọc danh sách theo phiếu mượn chưa trả
            return true;
        } else if (itemId == R.id.filter_by_returned) {
            filterListByReturned(); // Lọc danh sách theo phiếu mượn đã trả
            return true;
        } else {
            return super.onOptionsItemSelected(item); // Xử lý các lựa chọn khác (nếu có)
        }
    }



    private void searchDatabase(String query) {
        filteredList.clear(); // Xóa danh sách lọc hiện tại
        for (PhieuMuon phieuMuon : list) {
            if (phieuMuon.getTennd().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(phieuMuon); // Thêm phiếu mượn vào danh sách lọc nếu tên người dùng chứa từ khóa tìm kiếm
            }
        }
        loadPhieuMuon(); // Cập nhật danh sách trên giao diện
    }


    private void sortListByName() {

        Collections.sort(filteredList, new Comparator<PhieuMuon>() {
            @Override
            public int compare(PhieuMuon o1, PhieuMuon o2) {
                return o1.getTennd().compareTo(o2.getTennd()); // So sánh tên người dùng
            }
        });
        loadPhieuMuon(); // Cập nhật danh sách trên giao diện
    }

    private void sortListByDate() {
        Collections.sort(filteredList, new Comparator<PhieuMuon>() {
            @Override
            public int compare(PhieuMuon o1, PhieuMuon o2) {
                return o1.getNgaymuon().compareTo(o2.getNgaymuon()); // So sánh ngày mượn
            }
        });
        loadPhieuMuon(); // Cập nhật danh sách trên giao diện
    }

    private void filterListByBorrowed() {
        filteredList.clear(); // Xóa danh sách lọc hiện tại
        for (PhieuMuon phieuMuon : list) {
            if (phieuMuon.getNgaytra().isEmpty()) {
                filteredList.add(phieuMuon); // Thêm phiếu mượn chưa trả vào danh sách lọc
            }
        }
        loadPhieuMuon(); // Cập nhật danh sách trên giao diện
    }

    private void filterListByReturned() {
        filteredList.clear(); // Xóa danh sách lọc hiện tại
        for (PhieuMuon phieuMuon : list) {
            if (!phieuMuon.getNgaytra().isEmpty()) {
                filteredList.add(phieuMuon); // Thêm phiếu mượn đã trả vào danh sách lọc
            }
        }
        loadPhieuMuon(); // Cập nhật danh sách trên giao diện
    }

    public void showDialogpm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Tạo AlertDialog
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_phieumuon, null); // Nạp layout của dialog
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); // Đặt nền trong suốt cho dialog
        alertDialog.setCancelable(false); // Không cho phép hủy dialog bằng cách nhấn ra ngoài
        alertDialog.show();

        EditText etngaymuon = view.findViewById(R.id.etngaymuon); // Liên kết EditText ngày mượn
        EditText etngaytra = view.findViewById(R.id.etngaytra); // Liên kết EditText ngày trả
        EditText etmanguoimuon = view.findViewById(R.id.etnguoimuon); // Liên kết EditText mã người mượn
        Button btluu = view.findViewById(R.id.btluu); // Liên kết nút lưu
        Button bthuy = view.findViewById(R.id.bthuy); // Liên kết nút hủy

        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngaymuon = etngaymuon.getText().toString();
                String ngaytra = etngaytra.getText().toString();
                int maNguoiMuon = Integer.parseInt(etmanguoimuon.getText().toString());

                boolean check = phieuMuonDAO.thempm(ngaymuon, ngaytra, maNguoiMuon); // Thêm phiếu mượn vào cơ sở dữ liệu

                if (check) {
                    Toast.makeText(PhieuMuonMainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    list = phieuMuonDAO.getDSPhieuMuon(); // Lấy lại danh sách phiếu mượn
                    filteredList.clear();
                    filteredList.addAll(list); // Cập nhật danh sách lọc
                    loadPhieuMuon(); // Cập nhật danh sách trên giao diện
                    alertDialog.dismiss(); // Đóng dialog
                } else {
                    Toast.makeText(PhieuMuonMainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss(); // Đóng dialog khi nhấn nút hủy
            }
        });
    }

}
