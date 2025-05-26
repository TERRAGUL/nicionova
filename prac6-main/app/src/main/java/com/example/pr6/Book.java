package com.example.pr6;
import java.io.Serializable;


public class Book implements Serializable{
    private int ID_Book;
    private String Book_name;
    private String Book_author;

    public Book(int ID_Book, String book_Name, String book_Author){
        this.ID_Book = ID_Book;
        this.Book_name = book_Name;
        this.Book_author = book_Author;
    }

    public int getID_Book() {
        return ID_Book;
    }

    public void setID_Book(int ID_Book) {
        this.ID_Book = ID_Book;
    }

    public void setBook_author(String book_author) {
        Book_author = book_author;
    }

    public void setBook_name(String book_name) {
        Book_name = book_name;
    }

    public String getBook_name() {
        return Book_name;
    }

    public String getBook_author() {
        return Book_author;
    }
}
