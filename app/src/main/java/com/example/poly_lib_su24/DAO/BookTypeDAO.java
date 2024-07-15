package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DbHelper;
import com.example.poly_lib_su24.database.DpHelper;
import com.example.poly_lib_su24.model.BookType;

import java.util.ArrayList;

public class BookTypeDAO {
    DpHelper helper;
    public BookTypeDAO(Context context){
        helper =  new DpHelper(context);
    }
    public void addBook(BookType bookType){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloaisach", bookType.getTenLoaiSach());
        db.insert("loaisach",  null, values);
    }
    public ArrayList<BookType> getAllBookType(){
        ArrayList<BookType> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        // cursor con tro de chi du lieu
        Cursor c = db.rawQuery("select * from loaisach", null);
        if(c.moveToNext()){
            do{
                int maLoai = c.getInt(0);
                String tenLoaiSach = c.getString(1);
                BookType bookType = new BookType(maLoai, tenLoaiSach);
                list.add(bookType);
            }
            while (c.moveToNext());
        }
        return list;
    }
    public void delete(int maLoai){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("loaisach", "maloai=?", new String[]{maLoai+""});
    }
}
