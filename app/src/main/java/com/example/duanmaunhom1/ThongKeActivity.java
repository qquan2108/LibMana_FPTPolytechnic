package com.example.duanmaunhom1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanmaunhom1.DAO.ThongkeDAO;

import java.util.Calendar;

public class ThongKeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke);
        EditText edstarst= findViewById(R.id.edstart);
        EditText edend=findViewById(R.id.edend);
        Button btthongke=findViewById(R.id.btthongke);
        TextView txtketqua=findViewById(R.id.tvketqua);
        Calendar calendar = Calendar.getInstance();
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


        edstarst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ThongKeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay="";
                        String thang="";

                        if (dayOfMonth < 10){
                            ngay = "0" +1;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if ((month + 1)<10){
                            thang = "0" +(month + 1);
                        }else {
                            thang = String.valueOf((month +1));
                        }
                        edstarst.setText(year+"/"+thang +"/"+ ngay);


                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)

                );
                datePickerDialog.show();
            }
        });
        edend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ThongKeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay="";
                        String thang="";

                        if (dayOfMonth < 10){
                            ngay = "0" +1;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if ((month + 1)<10){
                            thang = "0" +(month + 1);
                        }else {
                            thang = String.valueOf((month +1));
                        }
                        edend.setText(year+"/"+thang +"/"+ ngay);


                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)

                );
                datePickerDialog.show();

            }
        });
        btthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongkeDAO thongkeDAO = new ThongkeDAO(ThongKeActivity.this);
                String ngaybatdau = edstarst.getText().toString();
                String ngayketthuc = edend.getText().toString();
                int nguoimuon = thongkeDAO.getDoanhThu(ngaybatdau,ngayketthuc);
                txtketqua.setText(nguoimuon + " Người mượn");
            }
        });




    }
}