package com.example.poly_lib_su24;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.SearchView;
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
    BookAdapter adapter;
    FloatingActionButton fButton;
    String filePath="";
    ImageView imgSach;
    TextView txtTrangThai;
    String linkHinh = "";
    SearchView searchView;
    ArrayList<Books> filteredBooks;

    private final int PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_books);

        toolbar = findViewById(R.id.toolBarCustom);
        rvCustomers = findViewById(R.id.recyclerView);
        fButton = findViewById(R.id.fButton);
        searchView = findViewById(R.id.searchView);

        booksDAO = new BooksDAO(BooksActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String tenLoai = getIntent().getStringExtra("tenLoai");
        getSupportActionBar().setTitle(tenLoai);

        int space = getResources().getDimensionPixelOffset(R.dimen.item_space);
        rvCustomers.addItemDecoration(new SpaceItem(space));

        requestPermission();
        configCloudinary();

        doDuLieu(null);

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemSach();
            }
        });
        listBooks = new ArrayList<>();
        filteredBooks = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng nhấn nút tìm kiếm
                // Thực hiện tìm kiếm với 'query'
                doDuLieu(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi nội dung tìm kiếm thay đổi
                // Cập nhật danh sách hiển thị theo 'newText'
                doDuLieu(newText);
                return false;
            }
        });
    }
    public void doDuLieu(String text){
        listBooks = booksDAO.getAllBooks();
        filteredBooks = new ArrayList<>();
        // Lấy thể loại từ Intent
        int bookType = getIntent().getIntExtra("bookType", -1);// Default là -1 nếu không tìm thấy giá trị
        // Lọc sách theo thể loại
        if(bookType != -1) {
            for (Books books : listBooks) {
                if (books.getMaLoai() == bookType) {
                    filteredBooks.add(books);
                }
            }
            adapter = new BookAdapter(BooksActivity.this, filteredBooks);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BooksActivity.this);
            rvCustomers.setLayoutManager(linearLayoutManager);
            rvCustomers.setAdapter(adapter);
        }
        // tìm kiếm
        else if(text!=null){
            for (Books book : listBooks) {
                if (book.getTenSach().toLowerCase().startsWith(text.toLowerCase())) {
                    filteredBooks.add(book);
                }
            }
            adapter = new BookAdapter(BooksActivity.this, filteredBooks);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BooksActivity.this);
            rvCustomers.setLayoutManager(linearLayoutManager);
            rvCustomers.setAdapter(adapter);
        }
        else{
            adapter = new BookAdapter(BooksActivity.this, listBooks);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BooksActivity.this);
            rvCustomers.setLayoutManager(linearLayoutManager);
            rvCustomers.setAdapter(adapter);
        }

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

        // Lấy mã loại và tên loại từ intent đã được gửi
        int maLoai = getIntent().getIntExtra("bookType", -1);
        String tenLoai = getIntent().getStringExtra("tenLoai");
        txtMaLoai.setText(maLoai+"");
        txtTheLoai.setText(tenLoai);

        imgSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
            }
        });
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
                        doDuLieu(null);
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
        imgSach = v.findViewById(R.id.imgSach);
        txtTrangThai = v.findViewById(R.id.txtTrangThai);

        Glide.with(this).load(books.getImg()).into(imgSach);

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
                Books booksNew = new Books(books.getMaSach(), tenSach, giaSach, books.getMaLoai(),  books.getTenLoaiSach(), linkHinh);
                booksDAO.edit(booksNew);
                doDuLieu(null);
                dialog.dismiss();
            }
        });
        imgSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
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
    public void deleteBook(int id, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(BooksActivity.this);
        builder.setTitle("Cảnh Báo");
        builder.setMessage("Bạn có muốn xóa thể loại này không?");
        builder.setCancelable(false);

        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                booksDAO.delete(id);
                doDuLieu(null);
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog adialog =  builder.create();
        adialog.show();
    }
    private void xoaKhoiDanhSach(int id, int position) {
        // Xóa sách khỏi danh sách và cập nhật RecyclerView
        for (int i = 0; i < listBooks.size(); i++) {
            if (listBooks.get(i).getMaSach() == id) {
                listBooks.remove(i);
                adapter.removeItem(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, listBooks.size());
                break;
            }
        }
    }
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Cấp quyền thành công", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Cấp quyền thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Truy cập hình ảnh
    public void accessTheGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        myLauncher.launch(i);
    }

    private ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //get the image's file location
            filePath = getRealPathFromUri(result.getData().getData(), BooksActivity.this);

            if (result.getResultCode() == RESULT_OK) {
                try {
                    //chọn ảnh vào imgLoaiSach
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getData().getData());
                    imgSach.setImageBitmap(bitmap);
                    uploadToCloudinary(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    private String getRealPathFromUri(Uri imageUri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if (cursor == null) {
            return imageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    HashMap<String, String> config = new HashMap<>();
    // Kiểm tra MediaManager đã tồn  tại chưa
    public void configCloudinary() {
        try {
            config.put("cloud_name", "namnn512");
            config.put("api_key", "212612511337371");
            config.put("api_secret", "_6OBeGuVwm9sAYbyW0u3lqMVYLE");
            MediaManager.init(BooksActivity.this, config);
        }catch (IllegalStateException e){

        }
    }
    public void uploadToCloudinary(String filePath) {
        Log.d("A", "sign up uploadToCloudinary- ");
        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                txtTrangThai.setText("Bắt đầu upload");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {txtTrangThai.setText("Đang upload... ");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                txtTrangThai.setText("Thành công: " + resultData.get("url").toString());
                linkHinh = resultData.get("url").toString();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                txtTrangThai.setText("Lỗi " + error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                txtTrangThai.setText("Reshedule " + error.getDescription());
            }
        }).dispatch();
    }
}