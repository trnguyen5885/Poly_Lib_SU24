package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DpHelper;
import com.example.poly_lib_su24.model.NguoiDung;

public class NguoiDungDAO {
    public DpHelper dpHelper;

    public NguoiDungDAO(Context context) {
        dpHelper = new DpHelper(context);
    }

}
