package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DbHelper;
import com.example.poly_lib_su24.model.BookType;

import java.util.ArrayList;

public class BookTypeDAO {
    DbHelper helper;
    public BookTypeDAO(Context context){
        helper =  new DbHelper(context);
    }
    public void addBook(BookType bookType){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", bookType.getName());
        db.insert("booktype",  null, values);
    }
    // xem sách
    public ArrayList<BookType> getAllBookType(){
        ArrayList<BookType> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        // cursor con tro de chi du lieu
        Cursor c = db.rawQuery("select * from booktype", null);
        if(c.moveToNext()){
            do{
                int id = c.getInt(0);
                String name = c.getString(1);
                BookType bookType = new BookType(id, name);
                list.add(bookType);
            }
            while (c.moveToNext());
        }
        return list;
    }
    public void delete(int id){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("booktype", "id=?", new String[]{id+""});
    }
}
