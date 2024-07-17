package com.example.poly_lib_su24;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.poly_lib_su24.adapter.BookAdapter;
import com.example.poly_lib_su24.DAO.BooksDAO;
import com.example.poly_lib_su24.model.BookType;
import com.example.poly_lib_su24.model.Books;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvCustomers;
    ArrayList<Books> listBooks = new ArrayList<Books>();
    BooksDAO booksDAO;
    FloatingActionButton fButton;
    String filePath="";
    ImageView imgSach;
    TextView txtTrangThai;
    String linkHinh = "";
    private final int PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books);

        toolbar = findViewById(R.id.toolBarCustom);
        rvCustomers = findViewById(R.id.recyclerView);
        fButton = findViewById(R.id.fButton);

        booksDAO = new BooksDAO(BooksActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String tenLoai = getIntent().getStringExtra("tenLoai");
        getSupportActionBar().setTitle(tenLoai);

        doDuLieu();

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemSach();
            }
        });
    }
    public void doDuLieu(){
        listBooks = booksDAO.getAllBooks();
        ArrayList<Books> filteredBooks = new ArrayList<>();
        // Lấy thể loại từ Intent
        int bookType = getIntent().getIntExtra("bookType", -1);// Default là -1 nếu không tìm thấy giá trị
        // Lọc sách theo thể loại
        for(Books books : listBooks) {
            if (books.getMaLoai() == bookType) {
                filteredBooks.add(books);
            }
        }
        BookAdapter adapter = new BookAdapter(BooksActivity.this, filteredBooks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BooksActivity.this);
        rvCustomers.setLayoutManager(linearLayoutManager);
        rvCustomers.setAdapter(adapter);
    }
    public void dialogThemSach(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BooksActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(v);
        AlertDialog dialog = builder.create();

        EditText edtTenLoaiSach = v.findViewById(R.id.edtTenSach);
        EditText edtGiaSach = v.findViewById(R.id.edtGiaSach);
        TextView txtTheLoai = v.findViewById(R.id.txtTheLoai);
        TextView txtMaLoai =  v.findViewById(R.id.txtMaLoai);
        Button btnThem = v.findViewById(R.id.btnThem);
        Button btnHuy = v.findViewById(R.id.btnHuy);
        imgSach = v.findViewById(R.id.imgSach);
        txtTrangThai = v.findViewById(R.id.txtTrangThai);
        int maLoai = getIntent().getIntExtra("bookType", -1);
        String tenLoai = getIntent().getStringExtra("tenLoai");
        txtMaLoai.setText(maLoai+"");
        txtTheLoai.setText(tenLoai);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenLoaiSach.getText().toString();
                int giaSach = Integer.parseInt(edtGiaSach.getText().toString());
                int maTheLoai = Integer.parseInt(txtMaLoai.getText().toString());
                if(tenSach.length() == 0||giaSach == 0){
                    Toast.makeText(BooksActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Books books = new Books(tenSach, giaSach, maTheLoai, linkHinh);
                    boolean check = booksDAO.addBook(books);
                    if (check) {
                        Toast.makeText(BooksActivity.this, "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        doDuLieu();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(BooksActivity.this, "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void suaSach(Books books){
        AlertDialog.Builder builder = new AlertDialog.Builder(BooksActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(v);

        EditText edtTenSach = v.findViewById(R.id.edtTenSach);
        EditText edtGiaSach = v.findViewById(R.id.edtGiaSach);
        TextView txtTheLoai = v.findViewById(R.id.txtTheLoai);

        TextView title = v.findViewById(R.id.title);

        title.setText("Sửa Sách");
        edtTenSach.setText(books.getTenSach());
        edtGiaSach.setText(String.valueOf(books.getGiaSach()));
        txtTheLoai.setText(books.getTenLoaiSach());

        AlertDialog dialog = builder.create();

        Button btnSua = v.findViewById(R.id.btnThem);
        Button btnHuy =  v.findViewById(R.id.btnHuy);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                int giaSach = Integer.parseInt(edtGiaSach.getText().toString());
                Books booksNew = new Books(books.getMaSach(), tenSach, giaSach, books.getMaLoai(),  books.getTenLoaiSach(), books.getImg());
                booksDAO.edit(booksNew);
                doDuLieu();
                dialog.dismiss();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}