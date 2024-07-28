package com.example.poly_lib_su24.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.R;
import com.example.poly_lib_su24.model.Books;
import com.example.poly_lib_su24.model.PhieuMuon;
import com.example.poly_lib_su24.viewholder.BookViewHolder;
import com.example.poly_lib_su24.viewholder.PhieuMuonViewHolder;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonViewHolder>{

    Context context;
    ArrayList<PhieuMuon> listPM;
    ArrayList<PhieuMuon> filteredListPhieuMuon;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> listPM) {
        this.context = context;
        this.listPM = listPM;
        this.filteredListPhieuMuon = new ArrayList<>(listPM);
    }
    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.phieumuon_viewholder_layout, parent,false);
        return new PhieuMuonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        holder.txtID.setText("Mã phiếu mượn "+ listPM.get(position).getMaPhieu() );
        holder.txtMaKH.setText("Mã Khách Hàng "+ listPM.get(position).getMaND() );
        holder.txtTenSach.setText("Tên sách "+ listPM.get(position).getTenSach() );
        holder.txtTienThue.setText("Tiền thuê "+ listPM.get(position).getGiathue() );
        holder.txtNgayThue.setText("Ngày thuê: "+ listPM.get(position).getNgayThue() );
        String trangthai ="";
        if(listPM.get(position).getTrangthai()==1){
            trangthai = "Đã trả sách";
        }else{
            trangthai = "Chưa trả sách";
            holder.txtCheck.setTextColor(Color.parseColor("#FFF44336"));
        }
        holder.txtCheck.setText("" + trangthai);

    }
    public void removeItemPM(int position) {
        listPM.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listPM.size());
    }
    @Override
    public int getItemCount() {
        return listPM.size();
    }

}
