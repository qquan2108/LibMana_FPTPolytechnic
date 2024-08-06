package com.example.duanmaunhom1.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmaunhom1.DAO.ThongkeDAO;
import com.example.duanmaunhom1.R;

import org.checkerframework.checker.units.qual.C;

import java.util.Calendar;

public class ThongkeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentthongke, container ,false);
        EditText edstarst= v.findViewById(R.id.edstart);
        EditText edend=v.findViewById(R.id.edend);
        Button btthongke=v.findViewById(R.id.btthongke);
        TextView txtketqua=v.findViewById(R.id.tvketqua);

        Calendar calendar = Calendar.getInstance();
        edstarst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
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
                        getContext(), new DatePickerDialog.OnDateSetListener() {
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
            ThongkeDAO thongkeDAO = new ThongkeDAO(getContext());
            String ngaybatdau = edstarst.getText().toString();
            String ngayketthuc = edend.getText().toString();
            int nguoimuon = thongkeDAO.getDoanhThu(ngaybatdau,ngayketthuc);
            txtketqua.setText(nguoimuon + " Người mượn");
        }
    });

        return v;



    }
}
