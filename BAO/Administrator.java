package BAO;
import DAO.*;
import DTO.*;
import javax.swing.*;
import java.util.List;

public class Administrator implements AdministratorInterface {
    public Administrator(){}
    private LibraryInterface library= FactoryDAO.getInstance();
    public void addBook(Book book) {
        library.addBook(book);
    }
    public void deleteBook( Book book) {
        library.deleteBook(book);
    }

    public void borrowBook(Book book , User user) {
        library.borrowBook(book,user);
    }

    public void returnBook(Book book,User user) {
        library.returnBook(book,user);
    }

    public void editBook(Book book) {
        library.editBook(book);

    }


    public List<Book> getAllBooks(){return library.getAllBooks();};
    public void notifyObservers(Book book){library.notifyObservers(book);}
}