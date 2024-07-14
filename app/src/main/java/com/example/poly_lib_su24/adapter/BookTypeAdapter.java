package com.example.poly_lib_su24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.BookTypeActivity;
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
        holder.txtID.setText(bookType.getId()+"");
        holder.txtName.setText(bookType.getName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = bookType.getId();
                ((BookTypeActivity)context).delete(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
