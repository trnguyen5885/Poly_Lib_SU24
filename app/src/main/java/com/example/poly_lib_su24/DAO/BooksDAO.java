package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DpHelper;
import com.example.poly_lib_su24.model.Books;

import java.util.ArrayList;


public class BooksDAO {
    DpHelper helper;
    public BooksDAO(Context context){
        helper = new DpHelper(context);
    }
    public void addBook(Books books){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", books.getTenSach());
        values.put("giasach", books.getGiaSach());
        values.put("maloai", books.getMaLoai());
        db.insert("sach",  null, values);
    }
    // xem sách
    public ArrayList<Books> getAllBooks(){
        ArrayList<Books> listBooks = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        // cursor con tro de chi du lieu
        Cursor c = db.rawQuery("select * from sach", null);
        if(c.moveToNext()){
            do{
                int maSach = c.getInt(0);
                String tenSach = c.getString(1);
                int giaBan = c.getInt(2);
                int maLoai = c.getInt(3);
                Books books = new Books(maSach, tenSach, giaBan, maLoai);
                listBooks.add(books);
            }
            while (c.moveToNext());
        }
        return listBooks;
    }

}
