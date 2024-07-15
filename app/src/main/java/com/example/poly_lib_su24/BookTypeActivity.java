package com.example.poly_lib_su24;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.adapter.BookTypeAdapter;
import com.example.poly_lib_su24.DAO.BookTypeDAO;
import com.example.poly_lib_su24.model.BookType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BookTypeActivity extends AppCompatActivity {
    Dialog currentDialog;
    RecyclerView rv;
    ArrayList<BookType> list = new ArrayList<>();
    BookTypeDAO bookTypeDAO;
    Toolbar toolbar;
    FloatingActionButton fButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_type);
        toolbar = findViewById(R.id.toolBar);
        rv = findViewById(R.id.rvBookType);
        fButton = findViewById(R.id.fButton);

        bookTypeDAO = new BookTypeDAO(BookTypeActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lí Loại Sách");

        doDuLieu();

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemTheLoai();
            }
        });

    }
    public void doDuLieu(){
        list = bookTypeDAO.getAllBookType();
        BookTypeAdapter bookTypeAdapter = new BookTypeAdapter(BookTypeActivity.this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookTypeActivity.this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(bookTypeAdapter);
    }
    public void dialogThemTheLoai(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BookTypeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_them_theloaisach, null);
        builder.setView(v);
        EditText edtTenLoaiSach = v.findViewById(R.id.edtTenLoaiSach);
        Button btnThem = v.findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiSach = edtTenLoaiSach.getText().toString();
                BookType bookType = new BookType(tenLoaiSach);
                bookTypeDAO.addBook(bookType);
                doDuLieu();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void suaTenLoaiSach(BookType bookType){
        AlertDialog.Builder builder = new AlertDialog.Builder(BookTypeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_them_theloaisach, null);
        builder.setView(v);

        EditText edtTenLoaiSach = v.findViewById(R.id.edtTenLoaiSach);
        TextView title = v.findViewById(R.id.title);
        title.setText("Sửa Loại Sách");
        edtTenLoaiSach.setText(bookType.getTenLoaiSach());

        Button btnSua = v.findViewById(R.id.btnThem);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTheLoaiSach = edtTenLoaiSach.getText().toString();
                BookType bookTypeNew = new BookType(bookType.getMaLoai(), tenTheLoaiSach);
                bookTypeDAO.edit(bookTypeNew);
                doDuLieu();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void delete(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(BookTypeActivity.this);
        builder.setTitle("Cảnh Báo");
        builder.setMessage("Bạn có muốn xóa thể loại này không?");
        builder.setCancelable(false);;
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bookTypeDAO.delete(id);
                doDuLieu();
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog =  builder.create();
        dialog.show();
    }

}