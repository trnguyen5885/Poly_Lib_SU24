package com.example.poly_lib_su24.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.R;

public class PhieuMuonViewHolder extends RecyclerView.ViewHolder{

    public TextView txtID,txtTenSach,txtMaKH,txtCheck,txtNgayThue,txtTienThue;
    public Button btnTraSach;
    public PhieuMuonViewHolder(@NonNull View itemView) {
        super(itemView);
        this.txtID = itemView.findViewById(R.id.txtIdTicket);
        this.txtMaKH = itemView.findViewById(R.id.txtCustomerID);
        this.txtTenSach = itemView.findViewById(R.id.txtBookName);
        this.txtTienThue = itemView.findViewById(R.id.txtBookPrice);
        this.txtNgayThue = itemView.findViewById(R.id.txtNgayThue);
        this.txtCheck = itemView.findViewById(R.id.txtCheck);
        this.btnTraSach = itemView.findViewById(R.id.btnTraSach);
    }
}
