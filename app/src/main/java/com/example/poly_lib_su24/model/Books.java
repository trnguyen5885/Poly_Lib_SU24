package com.example.poly_lib_su24.model;

public class Books {
    String bookTitle;
    int id;
    String price;
    String type;
    int img;
    public Books(int id, String bookTitle, String type, String price){
        this.id = id;
        this.bookTitle = bookTitle;
        this.type = type;
        this.price = price;
    }
    public Books(String bookTitle, String type, String price){
        this.bookTitle = bookTitle;
        this.type = type;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getType() {
        return type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

}
