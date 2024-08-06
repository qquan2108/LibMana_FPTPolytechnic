package com.example.duanmaunhom1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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

import com.example.duanmaunhom1.DAO.ChiTietpmDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.model.CTPhieuMuon;

import java.util.ArrayList;

public class CTPMAdapter extends RecyclerView.Adapter<CTPMAdapter.ViewHolder> {
    private Context context;
    public ArrayList<CTPhieuMuon> list;
    private ChiTietpmDAO CTPMDAO;

    public CTPMAdapter(Context context, ArrayList<CTPhieuMuon> list, ChiTietpmDAO CTPMDAO) {
        this.context = context;
        this.list = list;
        this.CTPMDAO = CTPMDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ctphieumuon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CTPhieuMuon item = list.get(position);

        holder.txtCTPM.setText("ID: " + item.getMactpm());
        holder.txtmapm.setText("Mã Phiếu Mượn: " + item.getMapm());
        holder.txtMaSach.setText("Mã Sách: " + item.getMasach());
        holder.txtSoLuong.setText("Số Lượng: " + item.getSoluong());
        holder.txtTenSach.setText("Tên Sách: " + item.getTensach());


        // Kiểm tra trạng thái của sách và cập nhật thông tin hiển thị
        String trangThai;
        if (item.getTrasach() == 1) {
            trangThai = "Đã trả sách";
            holder.btntrasach.setVisibility(View.GONE); // Ẩn nút trả sách nếu sách đã được trả
        } else {
            trangThai = "Chưa trả sách";
            holder.btntrasach.setVisibility(View.VISIBLE); // Hiển thị nút trả sách nếu sách chưa được trả
        }

// Cập nhật giao diện với trạng thái mới
        holder.txttrangthai.setText("Trạng Thái: " + trangThai);

// Debug log để kiểm tra giá trị của trạng thái
        Log.d("CTPMAdapter", "Trang Thai value: " + item.getTrasach() + " - " + trangThai);


        holder.txttrangthai.setText("Trạng Thái: " + trangThai);
        holder.btntrasach.setVisibility(item.getTrasach() == 0 ? View.VISIBLE : View.GONE);

        Log.d("CTPMAdapter", "Trangthai value: " + item.getTrasach() + " - " + trangThai);

        holder.btntrasach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kiemtra = CTPMDAO.thaydoitrangthai(item.getMactpm());
                if (kiemtra) {
                    // Cập nhật trạng thái trong danh sách hiện tại
                    item.setTrasach(1);
                    // Thông báo thay đổi dữ liệu để cập nhật lại giao diện
                    notifyItemChanged(holder.getAdapterPosition());
                    Toast.makeText(context, "Thay đổi trạng thái thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(item);
            }
        });

        holder.ivdele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCTPM, txtmapm, txtMaSach, txtSoLuong, txtTenSach, txttrangthai;
        Button btntrasach;
        ImageView ivedit, ivdele;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCTPM = itemView.findViewById(R.id.txtCTPM);
            txtmapm = itemView.findViewById(R.id.txtmapm);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txttrangthai = itemView.findViewById(R.id.txttrangthai);
            btntrasach = itemView.findViewById(R.id.btntrasach);
            ivedit = itemView.findViewById(R.id.ivEdit);
            ivdele = itemView.findViewById(R.id.ivDele);
        }
    }

    private void showDialogUpdate(CTPhieuMuon ctpm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_ctpm, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView tvtitle = view.findViewById(R.id.txttieude);
        EditText etmapm = view.findViewById(R.id.etmapm);
        EditText etmasach = view.findViewById(R.id.etmasach);
        EditText etsoluong = view.findViewById(R.id.etsoluong);
        Button btnluu = view.findViewById(R.id.btluu);
        Button btnhuy = view.findViewById(R.id.bthuy);

        tvtitle.setText("Cập Nhật Phiếu Mượn");
        btnluu.setText("Cập Nhật");
        etmapm.setText(String.valueOf(ctpm.getMapm()));
        etmasach.setText(String.valueOf(ctpm.getMasach()));
        etsoluong.setText(String.valueOf(ctpm.getSoluong()));

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mapm = Integer.parseInt(etmapm.getText().toString());
                int masach = Integer.parseInt(etmasach.getText().toString());
                int soluong = Integer.parseInt(etsoluong.getText().toString());

                CTPhieuMuon ctpmUpdate = new CTPhieuMuon(ctpm.getMactpm(), mapm, masach, soluong, ctpm.getTensach(), ctpm.getTrasach());

                boolean check = CTPMDAO.suaCTPM(ctpmUpdate);

                if (check) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loaddata();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void showDialogDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa CTPM " + list.get(position).getMactpm() + " không?");
        builder.setIcon(R.drawable.canhbao1);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int mactpm = list.get(position).getMactpm();
                CTPMDAO.xoactpm(mactpm);
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                loaddata();
            }
        });
        builder.setNegativeButton("Không", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loaddata() {
        list.clear();
        list.addAll(CTPMDAO.getCTPM());
        notifyDataSetChanged();
    }
}
