package BAO;

import DTO.Book;
import DTO.User;

import java.util.List;

public interface AdministratorInterface {
    public void addBook(Book book);
    public void deleteBook( Book book );
    public void borrowBook(Book book , User user);
    public void returnBook(Book book,User user);
    public void editBook(Book book);
    public void notifyObservers(Book book);
    public List<Book> getAllBooks();
}
