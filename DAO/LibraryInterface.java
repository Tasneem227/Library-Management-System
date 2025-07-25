package DAO;

import DTO.Book;
import DTO.User;

import java.util.List;

public interface LibraryInterface {
    public void addBook(Book book);
    public void deleteBook( Book book );
    public List<Book> getAllBooks();
    public void addUser( User user );
    public void borrowBook(Book book , User user);
    public void returnBook(Book book,User user);
    public void editBook(Book book);
    public void notifyObservers(Book book);
    public void addObserver(BookAvailabilityObserver observer);
    public void removeObserver(BookAvailabilityObserver observer);


}
