package com.example.poly_lib_su24.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.poly_lib_su24.BookTypeActivity;
import com.example.poly_lib_su24.BooksActivity;
import com.example.poly_lib_su24.R;
import com.example.poly_lib_su24.model.BookType;
import com.example.poly_lib_su24.viewholder.BookTypeViewHolder;

import java.util.ArrayList;

public class BookTypeAdapter extends RecyclerView.Adapter<BookTypeViewHolder> {
    Context context;
    ArrayList<BookType> list = new ArrayList<BookType>();
    public BookTypeAdapter(Context context, ArrayList<BookType> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public BookTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.booktype_viewholder_layout, null);
        return new BookTypeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookTypeViewHolder holder, int position) {
        BookType bookType = list.get(position);
        holder.txtID.setText(bookType.getMaLoai()+"");
        holder.txtName.setText(bookType.getTenLoaiSach());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = bookType.getMaLoai();
                ((BookTypeActivity)context).delete(id);
            }
        });
        // xu ly  hinh anh
        if(!list.get(position).getImg().equals("")){
            Glide.with(context).load(list.get(position).getImg()).into(holder.imgLoaiSach);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookType bookType = list.get(position);
                ((BookTypeActivity)context).suaTenLoaiSach(bookType);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = bookType.getMaLoai();
                String tenLoai = bookType.getTenLoaiSach();
                Intent intent = new Intent(context, BooksActivity.class);
                intent.putExtra("bookType", id);
                intent.putExtra("tenLoai", tenLoai);
                context.startActivity(intent);
            }
        });

        if(bookType.getXoa() == 0){
            holder.itemView.setVisibility(View.VISIBLE);
        }
        else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
