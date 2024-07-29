package com.example.poly_lib_su24.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DpHelper;

public class ThongKeDAO {
    DpHelper dpHelper;

    public ThongKeDAO(Context context) {
        dpHelper = new DpHelper(context);
    }

    public int ThongKeDoanhThu(String ngaybatdau, String ngayketthuc) {
        ngaybatdau = ngaybatdau.replace("-", "");
        ngayketthuc = ngayketthuc.replace("-","");
        SQLiteDatabase db = dpHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(giathue) FROM PHIEUMUON WHERE substr(ngaymuon,7)||substr(ngaymuon,4,2)||substr(ngaymuon,1,2) between ? and ?", new String[]{ngaybatdau,ngayketthuc});
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
