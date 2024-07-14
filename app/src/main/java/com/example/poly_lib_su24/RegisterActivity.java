package com.example.poly_lib_su24;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.poly_lib_su24.DAO.NguoiDungDAO;
import com.example.poly_lib_su24.model.NguoiDung;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;
    TextInputEditText txtDangNhap;
    TextInputEditText txtMatKhau;
    TextInputEditText txtXacNhanMatKhau;
    Button btnDangKi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtDangNhap = findViewById(R.id.txtDangNhap);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        txtXacNhanMatKhau = findViewById(R.id.txtXacNhanMatKhau);
        btnDangKi = findViewById(R.id.btnDangKi);
        nguoiDungDAO = new NguoiDungDAO(RegisterActivity.this);
        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tendangnhap = txtDangNhap.getText().toString();
                String matkhau = txtMatKhau.getText().toString();
                String xacnhanmatkhau = txtXacNhanMatKhau.getText().toString();

                if(xacnhanmatkhau.equals(matkhau)) {
                    NguoiDung nguoiDung = new NguoiDung(tendangnhap, xacnhanmatkhau);
                    boolean check = nguoiDungDAO.DangKyNguoiDung(nguoiDung);

                    if(check) {
                        Toast.makeText(RegisterActivity.this,"Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                    txtDangNhap.setText("");
                    txtMatKhau.setText("");
                    txtXacNhanMatKhau.setText("");
                    txtDangNhap.requestFocus();

                }



            }
        });


    }
}