package com.example.duanmaunhom1.fragment;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duanmaunhom1.DangNhap;
import com.example.duanmaunhom1.R;
import com.google.android.material.navigation.NavigationView;

public class trangchufragment extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trangchufragment);
        drawerLayout = findViewById(R.id.drawn_layout);
        toolbar = findViewById(R.id.toobar);
        navigationView = findViewById(R.id.nvView);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.menu);
        }

        // Lấy thông tin role từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);

        // Ẩn các mục menu không cần thiết dựa trên vai trò
        MenuItem qlsachItem = navigationView.getMenu().findItem(R.id.qlsach);
        MenuItem qlloaisachItem = navigationView.getMenu().findItem(R.id.qlloaisach);
        MenuItem qlthanhvienItem = navigationView.getMenu().findItem(R.id.qlthanhvien);
        MenuItem qlphieumuonItem = navigationView.getMenu().findItem(R.id.qlphieumuon);
        MenuItem thongkeItem = navigationView.getMenu().findItem(R.id.thongke);

        switch (role) {
            case 1: // user
                qlsachItem.setVisible(false);
                qlloaisachItem.setVisible(false);
                qlthanhvienItem.setVisible(false);
                qlphieumuonItem.setVisible(false);
                thongkeItem.setVisible(false);
                break;
            case 2: // thủ thư
                qlthanhvienItem.setVisible(false);
                thongkeItem.setVisible(false);
                break;
            case 3: // admin
                // Hiển thị tất cả các mục
                break;
            default:
                // Ẩn tất cả các mục nếu không xác định được vai trò
                qlsachItem.setVisible(false);
                qlloaisachItem.setVisible(false);
                qlthanhvienItem.setVisible(false);
                qlphieumuonItem.setVisible(false);
                thongkeItem.setVisible(false);
                break;
        }

        Fragment fragment = new trangchufragment1();
        FragmentManager fragment1 = getSupportFragmentManager();
        fragment1.beginTransaction()
                .replace(R.id.flCotent, fragment)
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                int id = menuItem.getItemId();

                Fragment fragment = null;
                if (id == android.R.id.home)
                    drawerLayout.openDrawer(GravityCompat.START);
                if (id == R.id.trangchu)
                    fragment = new trangchufragment1();
                else if (id == R.id.qlsach)
                    fragment = new sachfragment();
                else if (id == R.id.qlloaisach)
                    fragment = new loaisachfragment();
                else if (id == R.id.qlthanhvien)
                    fragment = new thanhvienfragment();
                else if (id == R.id.qlphieumuon)
                    fragment = new phieumuonfragment();
                else if (id == R.id.CTPM)
                    fragment = new ctmpfragment();
                else if (id == R.id.gioithieu)
                    fragment = new gioithieufragment();
                else if (id == R.id.caidat)
                    fragment = new ChangePassFragment();
                else if (id == R.id.thongke)
                    fragment = new ThongkeFragment();
                else if (id == R.id.sachfree)
                    fragment = new dosachFragment();
                else if (id == R.id.dangxuat) {
                    Intent i = new Intent(trangchufragment.this, DangNhap.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    return true;
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.flCotent, fragment)
                            .commit();
                    menuItem.setChecked(true);
                    setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}
