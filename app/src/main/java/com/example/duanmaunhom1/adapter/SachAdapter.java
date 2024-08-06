package com.example.duanmaunhom1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaunhom1.DAO.SachDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.model.Sach;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;

    private SachDAO sachDAO;



    public SachAdapter(Context context, ArrayList<Sach> list,SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaLoai.setText(("ID : " + list.get(position).getMasach()));
        holder.txtTensach.setText(list.get(position).getTensach());
        holder.txtTacGia.setText("Tác giả: " + list.get(position).getTacgia());
        holder.txtGiaBan.setText("Giá bán: " + list.get(position).getGiaban());
        holder.txtTenloai.setText("Thể loại: " + list.get(position).getTenloai());
        holder.ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdailogupdate(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa sách " + list.get(holder.getAdapterPosition()).getTensach() + " không ?");
                builder.setIcon(R.drawable.canhbao1);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = sachDAO.xoassach(list.get(holder.getAdapterPosition()).getMasach());
                        switch (check){

                            case -1:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Bạn cần xóa tất cả sách này trong CTPM", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                loaddata();
                                break;


                        }
                    }

                });
                builder.setNegativeButton("Không", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
//                int position = holder.getAdapterPosition();
//                int masach = list.get(position).getMasach();
//                sachDAO.xoassach(masach);
//                list.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, list.size());
//                Toast.makeText(context, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaLoai, txtTensach, txtTacGia, txtGiaBan, txtTenloai;
        ImageView ivedit,ivDele;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaLoai=itemView.findViewById(R.id.txtMaLoai);
            txtTensach=itemView.findViewById(R.id.txtTensach);
            txtTacGia=itemView.findViewById(R.id.txtTacGia);
            txtGiaBan=itemView.findViewById(R.id.txtGiaBan);
            txtTenloai=itemView.findViewById(R.id.txtTenloai);
            ivedit=itemView.findViewById(R.id.ivEdit);
            ivDele=itemView.findViewById(R.id.ivDele);
        }
    }

    public void showdailogupdate(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_sach, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView tvtitle = view.findViewById(R.id.txttieude);
        EditText edtTenSach = view.findViewById(R.id.ettensach);
        EditText edTacGia = view.findViewById(R.id.ettacgia);
        EditText edtTienthue = view.findViewById(R.id.ettienthue);
        EditText edttheloai = view.findViewById(R.id.ettenloai);
        Button btnluu = view.findViewById(R.id.btluu);
        Button btnHuy = view.findViewById(R.id.bthuy);

        tvtitle.setText("Sửa sach");
        btnluu.setText("Cập nhật");
        edtTenSach.setText(sach.getTensach());
        edTacGia.setText(sach.getTacgia());
        edtTienthue.setText(String.valueOf(sach.getGiaban()));
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
//                if (tenSach.equals("")){
//                    Toast.makeText(SachActivity.this, "Nhap ten sach", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String tacGia = edTacGia.getText().toString();
//                if (tacGia.equals("")){
//                    Toast.makeText(SachActivity.this, "Nhap ten tac gia", Toast.LENGTH_SHORT).show();
//                }
                int tienThue = Integer.parseInt(edtTienthue.getText().toString());

                int theLoai =  Integer.parseInt(edttheloai.getText().toString());

                Sach sach1 = new Sach(sach.getMasach(),tenSach,tacGia,tienThue,theLoai, sach.getTenloai());
                boolean check = sachDAO.suasach(sach1);
                if (check){
                    Toast.makeText(context, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    loaddata();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "Cap nhat that bai", Toast.LENGTH_SHORT).show();
                }


//
//                if (check){
//                    Toast.makeText(SachActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
//                    loadSach();
//                    alertDialog.dismiss();
//                }else {
//                    Toast.makeText(SachActivity.this, "Them that bai", Toast.LENGTH_SHORT).show();
//                }


            }
        });
            }
            private  void loaddata(){
        list.clear();
        list.addAll(sachDAO.getDSSach());
        notifyDataSetChanged();
            }
    }

