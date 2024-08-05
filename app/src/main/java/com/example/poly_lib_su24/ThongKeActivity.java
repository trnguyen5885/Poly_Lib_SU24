package com.example.poly_lib_su24;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.poly_lib_su24.DAO.ThongKeDAO;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class ThongKeActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextInputEditText txtNgayBatDau, txtNgayKetThuc;
    Button btnThongKe;
    TextView txtKetQua;
    ThongKeDAO thongKeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolBarTK);
        txtNgayBatDau = findViewById(R.id.txtNgayBatDau);
        txtNgayKetThuc = findViewById(R.id.txtNgayKetThuc);
        btnThongKe = findViewById(R.id.btnThongKe);
        txtKetQua = findViewById(R.id.txtResult);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thống kê doanh thu");

        Calendar calendar = Calendar.getInstance();

        txtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ThongKeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if(dayOfMonth < 10) {
                            ngay = "0" + dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if(month + 1 < 10) {
                            thang = "0" + (month + 1);
                        } else {
                            thang = String.valueOf((month + 1));
                        }
                        txtNgayBatDau.setText(year + "-" + thang + "-" + ngay);
                    }
                },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        txtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ThongKeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if(dayOfMonth < 10) {
                            ngay = "0" + dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if(month + 1 < 10) {
                            thang = "0" + (month + 1);
                        } else {
                            thang = String.valueOf((month + 1));
                        }
                        txtNgayKetThuc.setText(year + "-" + thang + "-" + ngay);
                    }
                },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongKeDAO = new ThongKeDAO(ThongKeActivity.this);
                String ngaybatdau = txtNgayBatDau.getText().toString();
                String ngayketthuc = txtNgayKetThuc.getText().toString();
                int doanhThu = thongKeDAO.ThongKeDoanhThu(ngaybatdau, ngayketthuc);
                if(doanhThu == 0) {
                    Toast.makeText(ThongKeActivity.this, " Ngày bắt đầu hoặc ngày kết thúc không có trong danh sách ", Toast.LENGTH_LONG).show();
                    txtNgayBatDau.setText("");
                    txtNgayKetThuc.setText("");
                    txtNgayBatDau.requestFocus();

                } else {
                    txtKetQua.setText(doanhThu + "" + "VNĐ");
                }

            }
        });
    }
}