package com.example.poly_lib_su24.model;

public class BookType {
    int id;
    String name;
    public BookType(int id, String name){
        this.id =  id;
        this.name = name;
    }
    public BookType(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
