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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.poly_lib_su24.DAO.ThanhVienDAO;
import com.example.poly_lib_su24.adapter.ThanhVienAdapter;
import com.example.poly_lib_su24.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThanhVienActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1;
    String linkHinh = "";
    RecyclerView rvCustomers;
    FloatingActionButton fbutton;
    ArrayList<ThanhVien> list = new ArrayList<>();
    ThanhVienDAO thanhVienDAO;
    Toolbar toolbar;
    ImageView imgThanhVien;
    TextView txtTrangThaitv;
    private Uri imageUri;
    private ThanhVienAdapter adapter;

    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_vien);

        toolbar = findViewById(R.id.toolBarCustom);
        rvCustomers = findViewById(R.id.recyclerThanhVien);
        fbutton = findViewById(R.id.themthanhvien);

        int space = getResources().getDimensionPixelOffset(R.dimen.item_space);
        rvCustomers.addItemDecoration(new SpaceItem(space));

        thanhVienDAO = new ThanhVienDAO(this);
        requestPermission();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lí Thành Viên");

        doDuLieu();
        configCloudinary();

        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemThanhVien();
            }
        });

    }


    public void doDuLieu() {
        list = thanhVienDAO.getDSThanhVien();
        adapter = new ThanhVienAdapter(this, list); // Khởi tạo adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvCustomers.setLayoutManager(linearLayoutManager);
        rvCustomers.setAdapter(adapter); // Gán adapter cho RecyclerView
    }


    private void dialogThemThanhVien() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_them_thanhvien, null);

        EditText edtMaThanhVien = dialogView.findViewById(R.id.edtMaThanhVien);
        EditText edtTenThanhVien = dialogView.findViewById(R.id.edtTenThanhVien);
        imgThanhVien = dialogView.findViewById(R.id.imgThanhVien);
        txtTrangThaitv = dialogView.findViewById(R.id.txtTrangThaitv);
        Button btnThem = dialogView.findViewById(R.id.btnThem);
        Button btnHuy = dialogView.findViewById(R.id.btnHuy);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        imgThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maThanhVienStr = edtMaThanhVien.getText().toString();
                String tenThanhVien = edtTenThanhVien.getText().toString();

                if (maThanhVienStr.isEmpty() || tenThanhVien.isEmpty()) {
                    Toast.makeText(ThanhVienActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int maThanhVien = Integer.parseInt(maThanhVienStr);
                String imgPath = imageUri != null ? imageUri.toString() : "";

                ThanhVien newMember = new ThanhVien(maThanhVien, tenThanhVien, imgPath, 0);
                thanhVienDAO.addDS(newMember);

                list.add(newMember);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
                doDuLieu();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void suaTenThanhVien(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(ThanhVienActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sua_thanhvien, null);
        builder.setView(v);

        EditText edtTenThanhVien = v.findViewById(R.id.edtTenThanhVien);
        EditText edtMaThanhVien = v.findViewById(R.id.edtMaThanhVien);
        TextView title = v.findViewById(R.id.title);
        imgThanhVien = v.findViewById(R.id.imgThanhVien);
        txtTrangThaitv = v.findViewById(R.id.txtTrangThaitv);

        title.setText("Sửa Loại Sách");
        edtMaThanhVien.setText(String.valueOf(thanhVien.getMathanhvien()));
        edtTenThanhVien.setText(thanhVien.getTenthanhvien());
        linkHinh = thanhVien.getImg(); // Đặt link hình ảnh hiện tại
        AlertDialog dialog = builder.create();

        Button btnSua = v.findViewById(R.id.btnSuatv);
        Button btnHuy =  v.findViewById(R.id.btnHuytv);

        imgThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenThanhVien = edtTenThanhVien.getText().toString();
                ThanhVien ThanhvienNew = new ThanhVien(thanhVien.getMathanhvien(), tenThanhVien, linkHinh);
                thanhVienDAO.edittv(ThanhvienNew);
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

    //cấp quyền truy cập
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Cấp quyền thành công", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    // Truy cập hình ảnh
    public void accessTheGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        myLauncher.launch(i);
    }

    private ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //get the image's file location
            String filePath = getRealPathFromUri(result.getData().getData(), ThanhVienActivity.this);

            if (result.getResultCode() == RESULT_OK) {
                try {
                    //chọn ảnh vào imgLoaiSach
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getData().getData());
                    imgThanhVien.setImageBitmap(bitmap);
                    uploadToCloudinary(linkHinh);
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
            MediaManager.init(ThanhVienActivity.this, config);
        }catch (IllegalStateException e) {

        }
    }
    public void uploadToCloudinary(String filePath) {
        Log.d("A", "sign up uploadToCloudinary- ");
        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                txtTrangThaitv.setText("Bắt đầu upload");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {txtTrangThaitv.setText("Đang upload... ");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                txtTrangThaitv.setText("Thành công: " + resultData.get("url").toString());
                linkHinh = resultData.get("url").toString();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                txtTrangThaitv.setText("Lỗi " + error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                txtTrangThaitv.setText("Reshedule " + error.getDescription());
            }
        }).dispatch();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ImageView imgThanhVien = findViewById(R.id.imgThanhVien);
            imgThanhVien.setImageURI(imageUri);
        }
    }


}
