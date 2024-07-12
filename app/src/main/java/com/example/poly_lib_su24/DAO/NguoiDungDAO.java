package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DpHelper;
import com.example.poly_lib_su24.model.NguoiDung;

public class NguoiDungDAO {

    public DpHelper dpHelper;

    public NguoiDungDAO(Context context) {
        dpHelper = new DpHelper(context);
    }

    public boolean KiemTraDangNhap(String username, String pass) {
        SQLiteDatabase db = dpHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?",
                new String[]{username,pass});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DangKyNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase db = dpHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tendangnhap",nguoiDung.getTendangnhap());
        values.put("matkhau", nguoiDung.getMatkhau());
        long check = db.insert("NGUOIDUNG", null, values);
        return check != -1;
    }


}
