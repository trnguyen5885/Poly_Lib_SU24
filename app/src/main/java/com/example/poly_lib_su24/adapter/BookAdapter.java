package com.example.poly_lib_su24.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
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
import com.example.poly_lib_su24.model.Books;
import com.example.poly_lib_su24.viewholder.BookViewHolder;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    Context context;
    ArrayList<Books> listBook;


    public BookAdapter(Context context, ArrayList<Books> listBook) {
        this.context = context;
        this.listBook = listBook;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_viewholder_layout, null);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Books books = listBook.get(position);

        // Định dạng phần Mã sách đậm
        String maSachText = "Mã sách: ";
        int maSach = books.getMaSach();
        SpannableStringBuilder spannableMaSach = new SpannableStringBuilder(maSachText + maSach);
        spannableMaSach.setSpan(new StyleSpan(Typeface.BOLD), maSachText.length(), spannableMaSach.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtID.setText(spannableMaSach);

        // Định dạng phần Tên sách đậm
        String tenSachText = "Tên: ";
        String tenSach = books.getTenSach();
        SpannableStringBuilder spannableTenSach = new SpannableStringBuilder(tenSachText + tenSach);
        spannableTenSach.setSpan(new StyleSpan(Typeface.BOLD), tenSachText.length(), spannableTenSach.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtBookTitle.setText(spannableTenSach);

        // Định dạng phần Thể loại đậm
        String theLoaiText = "Thể loại: ";
        String theLoai = books.getTenLoaiSach();
        SpannableStringBuilder spannableTheLoai = new SpannableStringBuilder(theLoaiText + theLoai);
        spannableTheLoai.setSpan(new StyleSpan(Typeface.BOLD), theLoaiText.length(), spannableTheLoai.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtType.setText(spannableTheLoai);

        // Định dạng phần Giá sách đậm
        String giaSachText = "Giá: ";
        int giaSach = books.getGiaSach();
        String vnd = " VND";
        SpannableStringBuilder spannableGiaSach = new SpannableStringBuilder(giaSachText + giaSach + vnd);
        spannableGiaSach.setSpan(new StyleSpan(Typeface.BOLD), giaSachText.length(), spannableGiaSach.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtPrice.setText(spannableGiaSach);

        // xu ly  hinh anh
        if(!listBook.get(position).getImg().equals("")){
            Glide.with(context).load(listBook.get(position).getImg()).into(holder.imgBook);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Books book = listBook.get(position);
                ((BooksActivity)context).suaSach(book);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = books.getMaSach();
                ((BooksActivity)context).deleteBook(id, holder.getAdapterPosition());
            }
        });
     if(books.getXoaSach() == 0){
          holder.itemView.setVisibility(View.VISIBLE);
      }
      else {
          holder.itemView.setVisibility(View.GONE);
        }
   }
    public void removeItem(int position) {
        listBook.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listBook.size());
    }
    @Override
    public int getItemCount() {
        return listBook.size();
    }

}

