package DAO;
import DTO.User;
import DTO.Book;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import BAO.Administrator;
public class Library implements LibraryInterface{
    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<BookAvailabilityObserver> observers = new ArrayList<>();
    private  final static Library singleInstance=new Library();
    private Library(){

    }

    public static Library singleInstance() {

        return singleInstance;
    }
    Book book1 = new Book("Lord of The Rings", "F.S.M", "123456", "Fiction");
    Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "000111", "Fiction");
    Book book3 = new Book("Harry Potter", "J.K Rowling", "222333", "Fiction");
    { addBook(book1);
        addBook(book2);
        addBook(book3);}
    public void addObserver(BookAvailabilityObserver observer){
        observers.add(observer);
    }
    public void removeObserver(BookAvailabilityObserver observer){
        observers.remove(observer);
    }
    public void notifyObservers(Book book) {
        for (BookAvailabilityObserver observer : observers){
            observer.updateAvailability(book);  // From the Interface of Observer
        }
    }


    public void addBook(Book book){
        books.add(book); // added to the list of books
        notifyObservers(book);
    }

    public void deleteBook( Book book ){
        books.remove(book);
        notifyObservers(book);
    }
    public List<Book> getAllBooks() {
        return books;
    }
    public void addUser( User user ){
        users.add(user);
    }


    public void borrowBook(Book book , User user){
        if(book.isAvailable()){
            book.setAvailability(false);
            book.setBorrowedBy(user);
            notifyObservers(book);
            JOptionPane.showMessageDialog(null, "user :- \""+user.getName() +"\""+ " Borrowed book: "+"( "+book.getTitle()+" )","Borrow Book",JOptionPane.INFORMATION_MESSAGE);
        } else{
            JOptionPane.showMessageDialog(null,"Sorry, the book " +book.getTitle() + "is not available for borrowing", "Borrow Book Error",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void returnBook(Book book,User user) {
        if (user.getName().equals(book.getBorrowedBy().getName())
                && user.getContactInformation().equals(book.getBorrowedBy() .getContactInformation())
                &&user.getId().equals(book.getBorrowedBy().getId())) {
            book.setAvailability(true);
            book.setBorrowedBy(null);
            notifyObservers(book);
            JOptionPane.showMessageDialog(null, user.getName() + " Returned Book "+book.getTitle(),"Returning Book",JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "Book Returned SUCCESSFULLY");

        }
        else {
            JOptionPane.showMessageDialog(null, " The Book you entered Isn't Borrowed by You ","Returning Book",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void editBook(Book book){
        notifyObservers(book);
    }
}