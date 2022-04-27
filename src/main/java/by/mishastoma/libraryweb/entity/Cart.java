package by.mishastoma.libraryweb.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Book> books;
    public Cart(){
        books = new ArrayList<>();
        //todo
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void add(Book book){
        books.add(book);
    }

    public void remove(Book book){
        books.remove(book);
    }

    public Book get(int index){
        try{
            return books.get(index); //todo mb change approach
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

    }

    public int calcPrice(){
        return books.stream().mapToInt(Book::getPrice).sum();
    }
}
