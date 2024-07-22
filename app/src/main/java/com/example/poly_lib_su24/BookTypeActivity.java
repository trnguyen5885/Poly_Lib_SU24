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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.poly_lib_su24.adapter.BookTypeAdapter;
import com.example.poly_lib_su24.DAO.BookTypeDAO;
import com.example.poly_lib_su24.model.BookType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookTypeActivity extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<BookType> list = new ArrayList<>();
    BookTypeDAO bookTypeDAO;
    Toolbar toolbar;
    FloatingActionButton fButton;
    String filePath="";
    ImageView imgLoaiSach;
    TextView txtTrangThai;
    String linkHinh = "";
    private final int PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        toolbar = findViewById(R.id.toolBar);
        rv = findViewById(R.id.rvBookType);
        fButton = findViewById(R.id.fButton);

        int space = getResources().getDimensionPixelOffset(R.dimen.item_space);
        rv.addItemDecoration(new SpaceItem(space));

        bookTypeDAO = new BookTypeDAO(BookTypeActivity.this);
        requestPermission();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lí Loại Sách");

        doDuLieu();
        configCloudinary();

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
        AlertDialog dialog = builder.create();

        EditText edtTenLoaiSach = v.findViewById(R.id.edtTenLoaiSach);
        Button btnThem = v.findViewById(R.id.btnThem);
        Button btnHuy = v.findViewById(R.id.btnHuy);
        imgLoaiSach = v.findViewById(R.id.imgLoaiSach);
        txtTrangThai = v.findViewById(R.id.txtTrangThai);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoaiSach = edtTenLoaiSach.getText().toString();
                if(tenLoaiSach.length() == 0){
                    Toast.makeText(BookTypeActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    BookType bookType = new BookType(tenLoaiSach, linkHinh);
                    boolean check = bookTypeDAO.addBook(bookType);
                    if (check) {
                        Toast.makeText(BookTypeActivity.this, "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                        doDuLieu();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(BookTypeActivity.this, "Thêm loại sách thất bại", Toast.LENGTH_SHORT).show();
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
        imgLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
            }
        });
        dialog.show();
    }
    public void suaTenLoaiSach(BookType bookType){
        AlertDialog.Builder builder = new AlertDialog.Builder(BookTypeActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_them_theloaisach, null);
        builder.setView(v);

        EditText edtTenLoaiSach = v.findViewById(R.id.edtTenLoaiSach);
        TextView title = v.findViewById(R.id.title);
        imgLoaiSach = v.findViewById(R.id.imgLoaiSach);
        txtTrangThai = v.findViewById(R.id.txtTrangThai);

        Glide.with(this).load(bookType.getImg()).into(imgLoaiSach);

        title.setText("Sửa Loại Sách");
        edtTenLoaiSach.setText(bookType.getTenLoaiSach());
        AlertDialog dialog = builder.create();

        Button btnSua = v.findViewById(R.id.btnThem);
        Button btnHuy =  v.findViewById(R.id.btnHuy);

        imgLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTheLoaiSach = edtTenLoaiSach.getText().toString();
                BookType bookTypeNew = new BookType(bookType.getMaLoai(), tenTheLoaiSach,linkHinh);
                bookTypeDAO.edit(bookTypeNew);
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
    public void delete(int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(BookTypeActivity.this);
        builder.setTitle("Cảnh Báo");
        builder.setMessage("Bạn có muốn xóa thể loại này không?");
        builder.setCancelable(false);

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
            }
        });

        AlertDialog adialog =  builder.create();
        adialog.show();
    }



    // Cấp quyền truy cập bộ nhớ
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
            filePath = getRealPathFromUri(result.getData().getData(), BookTypeActivity.this);

            if (result.getResultCode() == RESULT_OK) {
                try {
                    //chọn ảnh vào imgLoaiSach
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getData().getData());
                    imgLoaiSach.setImageBitmap(bitmap);
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
    // Upload lên clound
    public void configCloudinary() {
        try {
            config.put("cloud_name", "namnn512");
            config.put("api_key", "212612511337371");
            config.put("api_secret", "_6OBeGuVwm9sAYbyW0u3lqMVYLE");
            MediaManager.init(BookTypeActivity.this, config);
        }catch (IllegalStateException e) {

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