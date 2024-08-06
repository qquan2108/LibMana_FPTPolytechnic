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

import com.example.duanmaunhom1.DAO.PhieuMuonDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonAdapter extends  RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PhieuMuon> list;
    private PhieuMuonDAO phieuMuonDAO;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list, PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.list = list;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_qlphieumuon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaPhieuMuon.setText("ID : " + list.get(position).getMapm());
        holder.txtNgayMuon.setText("Ngày Mượn : " + list.get(position).getNgaymuon());
        holder.txtNgayTra.setText("Ngày Trả : " + list.get(position).getNgaytra());
        holder.txttennd.setText("Người Mượn : " + list.get(position).getTennd());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdailogphieumuon(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa phiếu mượn" + list.get(holder.getAdapterPosition()).getMapm() + " không?");
                builder.setIcon(R.drawable.canhbao1);
                builder.setPositiveButton("co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = phieuMuonDAO.xoapm(list.get(holder.getAdapterPosition()).getMapm());
                        switch (check){
                            case -1:
                                Toast.makeText(context, "Không thể xóa ", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Bạn cần xóa ctpm có mã pm này", Toast.LENGTH_SHORT).show();
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
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size()  ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaPhieuMuon, txtNgayMuon, txtNgayTra, txttennd;
        ImageView ivEdit, ivDele;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPhieuMuon = itemView.findViewById(R.id.txtMaPhieuMuon);
            txtNgayMuon = itemView.findViewById(R.id.txtNgayMuon);
            txtNgayTra = itemView.findViewById(R.id.txtNgayTra);
            txttennd = itemView.findViewById(R.id.txttennd);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDele = itemView.findViewById(R.id.ivDele);

        }
    }
    public void showdailogphieumuon(PhieuMuon phieuMuon){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_phieumuon, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView tvtitle = view.findViewById(R.id.txttieude);
        EditText etngaymuon = view.findViewById(R.id.etngaymuon);
        EditText etngaytra = view.findViewById(R.id.etngaytra);
        EditText etmanguoimuon = view.findViewById(R.id.etnguoimuon);

        Button btluu = view.findViewById(R.id.btluu);
        Button bthuy = view.findViewById(R.id.bthuy);

        tvtitle.setText("Sửa sách");
        btluu.setText("Cập nhật");
        etngaymuon.setText(phieuMuon.getNgaymuon());
        etngaytra.setText(phieuMuon.getNgaytra());
        etmanguoimuon.setText(phieuMuon.getTennd());
        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngaymuon = etngaymuon.getText().toString();
                String ngaytra = etngaytra.getText().toString();
                int maNguoiMuon = Integer.parseInt(etmanguoimuon.getText().toString());

                PhieuMuon phieumuonupdate = new PhieuMuon(phieuMuon.getMapm(), ngaymuon, ngaytra, maNguoiMuon,phieuMuon.getTennd());

               boolean check =  phieuMuonDAO.suaphiuemuon(phieumuonupdate);

               if (check){
                   loaddata();
                   Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
               }else {
                   Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
    private  void loaddata(){
        list.clear();
        list = phieuMuonDAO.getDSPhieuMuon();
        notifyDataSetChanged();
    }
}
