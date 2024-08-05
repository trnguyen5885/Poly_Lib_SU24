package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DpHelper;
import com.example.poly_lib_su24.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {
    private DpHelper helper;

    public ThanhVienDAO(Context context) {
        helper = new DpHelper(context);
    }

    public boolean addDS(ThanhVien thanhVien) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mathanhvien", thanhVien.getMathanhvien());
        values.put("tenthanhvien", thanhVien.getTenthanhvien());
        values.put("img", thanhVien.getImg());
        values.put("xoa", thanhVien.getXoa());

        long check = db.insert("THANHVIEN", null, values);
        db.close();
        return check != -1;
    }


    public ArrayList<ThanhVien> getDSThanhVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from thanhvien", null);
        if(c.moveToFirst()){
            do{
                int maThanhVien = c.getInt(0);
                String tenThanhVien = c.getString(1);
                String img = c.getString(2);
                int xoa = c.getInt(3);
                ThanhVien thanhVien = new ThanhVien(maThanhVien, tenThanhVien, img, xoa);
                list.add(thanhVien);
            }
            while (c.moveToNext());
        }
        return list;
    }

    public void delete(int maThanhvien) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("xoa", 1);
        db.update("thanhvien", values, "mathanhvien=?", new String[]{maThanhvien+""});
    }

    public void edittv(ThanhVien thanhVien) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mathanhvien", thanhVien.getMathanhvien());
        values.put("tenthanhvien", thanhVien.getTenthanhvien());
        values.put("img", thanhVien.getImg());
        db.update("THANHVIEN", values, "mathanhvien=?", new String[]{thanhVien.getMathanhvien()+""});
    }
}

