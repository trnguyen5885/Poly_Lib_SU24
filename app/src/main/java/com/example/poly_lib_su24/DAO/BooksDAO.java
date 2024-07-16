package com.example.poly_lib_su24.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DpHelper;
import com.example.poly_lib_su24.model.BookType;
import com.example.poly_lib_su24.model.Books;

import java.util.ArrayList;


public class BooksDAO {
    DpHelper helper;
    public BooksDAO(Context context){
        helper = new DpHelper(context);
    }
    public boolean addBook(Books books){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", books.getTenSach());
        values.put("giasach", books.getGiaSach());
        values.put("maloai", books.getMaLoai());
        values.put("img", books.getImg());
        long check = db.insert("sach",  null, values);
        if(check == -1) return false;
        return true;
    }
    // xem sách
    public ArrayList<Books> getAllBooks(){
        ArrayList<Books> listBooks = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        // cursor con tro de chi du lieu
        // select s.masach, s.tensach, s.giasach, s.maloai, s.tenloai from sach s, loaisach l where s.maloai=?
        Cursor c = db.rawQuery("select s.masach, s.tensach, s.giasach, s.maloai, l.tenloaisach, s.img from sach s, loaisach l where s.maloai=l.maloai", null);
        if(c.moveToFirst()){
            do{
                int maSach = c.getInt(0);
                String tenSach = c.getString(1);
                int giaBan = c.getInt(2);
                int maLoai = c.getInt(3);
                String tenLoai = c.getString(4);
                String img = c.getString(5);
                Books books = new Books(maSach, tenSach, giaBan, maLoai, tenLoai, img);
                listBooks.add(books);
            }
            while (c.moveToNext());
        }
        return listBooks;
    }

    public void edit(Books books){
        SQLiteDatabase db =  helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", books.getTenSach());
        values.put("giasach", books.getGiaSach());
        db.update("sach", values, "masach=?", new String[]{books.getMaSach()+""});
    }
}
