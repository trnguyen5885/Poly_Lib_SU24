package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DbHelper;
import com.example.poly_lib_su24.model.Books;

import java.util.ArrayList;


public class BooksDAO {
    DbHelper helper;
    public BooksDAO(Context context){
        helper = new DbHelper(context);
    }
    public void addBook(Books books){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", books.getBookTitle());
        values.put("price", books.getPrice());
        values.put("type", books.getType());
        db.insert("books",  null, values);
    }
    // xem sách
    public ArrayList<Books> getAllBooks(){
        ArrayList<Books> listBooks = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        // cursor con tro de chi du lieu
        Cursor c = db.rawQuery("select * from books", null);
        if(c.moveToNext()){
            do{
                int id = c.getInt(0);
                String title = c.getString(1);
                String type = c.getString(2);
                String price = c.getString(3);
                Books books = new Books(id, title, type, price);
                listBooks.add(books);
            }
            while (c.moveToNext());
        }
        return listBooks;
    }

}
