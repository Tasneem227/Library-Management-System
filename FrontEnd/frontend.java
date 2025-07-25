package FrontEnd;
import BAO.*;
import DTO.*;;
import javax.swing.*;
import java.util.List;

public class frontend {

    AdministratorInterface administrator = FactoryBAO.getInstance();

    public void addBook() {
        String author="";
        String ISBN="";
        String genre="";
        // there are so many novels that have parts so we shouldn't use containsNumbers method;
        String  title = JOptionPane.showInputDialog("Enter the title of the book you want to add");
        if(FindBookByTitle(title)==null &&title != null &&!title.isBlank() && !title.isEmpty()&&hasNoSpecialCharacters(title)){
            author =  JOptionPane.showInputDialog("Enter the author of the book");
            if(author != null && !author.isEmpty() && containsNumbers(author)&&hasNoSpecialCharacters(author)&&!author.isBlank()){
                ISBN = JOptionPane.showInputDialog("Enter the ISBN of the book");
                if(ISBN != null && !ISBN.isEmpty() &&containsCharacters(ISBN)&&!ISBN.isBlank() &&hasNoSpecialCharacters(ISBN)&&!repeatedISBN(ISBN)) {
                    genre = JOptionPane.showInputDialog("Enter the genre of the book");
                    if(genre == null || genre.isEmpty() ||  genre.isBlank() || !hasNoSpecialCharacters(genre) ||  !containsNumbers(genre)){
                        JOptionPane.showMessageDialog(null, "Invalid Input!","ERROR" , JOptionPane.ERROR_MESSAGE);
                        reRunAdmin();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid ISBN!","ERROR" , JOptionPane.ERROR_MESSAGE);
                    reRunAdmin();
                }
            } else{
                JOptionPane.showMessageDialog(null, "Invalid Input!","ERROR" , JOptionPane.ERROR_MESSAGE);
                reRunAdmin();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Invalid title!","ERROR" , JOptionPane.ERROR_MESSAGE);
            reRunAdmin();
        }


        Book newBook = new Book(title, author, ISBN, genre);
        administrator.addBook(newBook);

        JOptionPane.showMessageDialog(null, "Book Added SUCCESSFULLY","add Book",JOptionPane.INFORMATION_MESSAGE);
        displayAllBooks(administrator.getAllBooks());
    }




    public void deleteBook() {

        String ISBN = JOptionPane.showInputDialog("Enter the ISBN of the book to delete it");
        Book bookToDelete = findBookByISBN(ISBN);

        if (bookToDelete != null) {
            administrator.deleteBook(bookToDelete);
            administrator.notifyObservers(bookToDelete);
            JOptionPane.showMessageDialog(null, "Book deleted SUCCESSFULLY");
            displayAllBooks(administrator.getAllBooks());
        } else {
            JOptionPane.showMessageDialog(null, "Book not found");

        }
    }

    public void borrowBook() {

        if(administrator.getAllBooks().isEmpty()){
            JOptionPane.showMessageDialog(null,"There are no books in the library to borrow");
            reRunAdmin();
        }
        String title = JOptionPane.showInputDialog("Enter the title of the book you want to borrow");
        if (title != null &&!title.isBlank() && !title.isEmpty() && hasNoSpecialCharacters(title)) {
            Book bookToBorrow = FindBookByTitle(title);

            if (bookToBorrow != null && bookToBorrow.isAvailable()) {
                String userName = JOptionPane.showInputDialog("Enter your name:");
                if (userName != null && !userName.isEmpty() && containsNumbers(userName) && hasNoSpecialCharacters(userName)&&!userName.isBlank()) {
                    String userId = JOptionPane.showInputDialog("Enter your ID");
                    if(userId != null && !userId.isEmpty() && containsCharacters(userId) && hasNoSpecialCharacters(userId)&&!userId.isBlank()){
                        String userContact = JOptionPane.showInputDialog("Enter your phone number: [11 digits please]");
                        if(userContact == null || userContact.isEmpty() || userContact.isBlank() || !validateElevenDigits(userContact) || !hasNoSpecialCharacters(userContact) || !containsCharacters(userContact)){
                            JOptionPane.showMessageDialog(null, "Invalid userContact please don't forget to put 11 digits", "Borrow Book Error", JOptionPane.ERROR_MESSAGE);
                            reRunUser();
                        } else{
                            User user = new User(userName, userId, userContact);
                            administrator.borrowBook(bookToBorrow, user);
                            JOptionPane.showMessageDialog(null, "Book Borrowed SUCCESSFULLY");
                            displayAllBooks(administrator.getAllBooks());
                        }
                    } else{
                        JOptionPane.showMessageDialog(null, "Invalid User ID", "Borrow Book Error", JOptionPane.ERROR_MESSAGE);
                        reRunUser();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid user name", "Borrow Book Error", JOptionPane.ERROR_MESSAGE);
                    reRunUser();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Book not Found", "Borrow Book Error", JOptionPane.ERROR_MESSAGE);
                reRunUser();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Input!","ERROR" , JOptionPane.ERROR_MESSAGE);
            reRunUser();
        }
    }

    public void ReturnBook() {

        if(administrator.getAllBooks().isEmpty()){
            JOptionPane.showMessageDialog(null,"There are no books in the library to return");
            reRunAdmin();
        }
        String title = JOptionPane.showInputDialog("Enter the title of the book you want to Return");
        if (FindBookByTitle(title) == null ||title == null || title.isBlank() || title.isEmpty() || !containsNumbers(title) || !hasNoSpecialCharacters(title) || FindBookByTitle(title).isAvailable()) {

            JOptionPane.showMessageDialog(null, "sorry , Invalid Input");
            reRunUser();
        }

        else {
            Book book = FindBookByTitle(title);
            String userName;
            String userId;
            String userContact = null;

            if (title != null && !title.isBlank() && !title.isEmpty() && containsNumbers(title) && hasNoSpecialCharacters(title) && !book.isAvailable()) {
                Book bookToReturn = FindBookByTitle(title);

                if (bookToReturn != null) {
                    userName = JOptionPane.showInputDialog("Enter your name:");
                    if (userName != null && !userName.isEmpty() && containsNumbers(userName) && hasNoSpecialCharacters(userName)&&!userName.isBlank()) {
                        userId = JOptionPane.showInputDialog("Enter your ID");
                        if (userId != null && !userId.isEmpty() && containsCharacters(userId) && hasNoSpecialCharacters(userId)&&!userId.isBlank()) {
                            userContact = JOptionPane.showInputDialog("Enter your phone number: [11 digits please]");
                            if (userContact == null || userContact.isEmpty() || userContact.isBlank() || !validateElevenDigits(userContact) || !hasNoSpecialCharacters(userContact) || !containsCharacters(userContact)) {
                                JOptionPane.showMessageDialog(null, "Invalid userContact please don't forget to put 11 digits", "Borrow Book Error", JOptionPane.ERROR_MESSAGE);

                            } else {
                                User user = new User(userName, userId, userContact);
                                administrator.returnBook(bookToReturn, user);
                                displayAllBooks(administrator.getAllBooks());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid user ID", "Borrow Book Error", JOptionPane.ERROR_MESSAGE);
                            reRunUser();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid user name", "Borrow Book Error", JOptionPane.ERROR_MESSAGE);
                        reRunUser();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Book not Found", "Borrow Book Error", JOptionPane.ERROR_MESSAGE);
                    reRunUser();
                }
            } else
                JOptionPane.showMessageDialog(null, "the Book you entered isn't borrowed ", "ERROR", JOptionPane.ERROR_MESSAGE);
            reRunUser();
        }
    }

    public void editBook() {

        String title="";
        String author="";
        String ISBN1="";
        String genre="";
        String ISBN = JOptionPane.showInputDialog("Enter the ISBN for the book you want to edit");
        Book bookToEdit = findBookByISBN(ISBN);
        if (ISBN != null && !ISBN.isEmpty() && containsCharacters(ISBN) && !ISBN.isBlank() && hasNoSpecialCharacters(ISBN)) {
            bookToEdit = findBookByISBN(ISBN);
            if (bookToEdit != null) {
                title = JOptionPane.showInputDialog("Enter the new Title of the Book");
                if (title != null && !title.isBlank() && !title.isEmpty()  && hasNoSpecialCharacters(title)) {

                    author = JOptionPane.showInputDialog("Enter the new Author of the Book");
                    if (author != null&& !title.isBlank() && !author.isEmpty() && containsNumbers(author) && hasNoSpecialCharacters(author)) {

                        ISBN1 = JOptionPane.showInputDialog("Enter the new ISBN of the book");
                        if (ISBN1 != null && !ISBN1.isEmpty() && containsCharacters(ISBN1) && !ISBN1.isBlank() && hasNoSpecialCharacters(ISBN1)) {

                            genre = JOptionPane.showInputDialog("Enter the new Genre of the book");
                            if (genre == null || genre.isEmpty() ||  genre.isBlank() || !hasNoSpecialCharacters(genre) ||  !containsNumbers(genre)) {
                                JOptionPane.showMessageDialog(null, "Invalid Genre !", "ERROR", JOptionPane.ERROR_MESSAGE);
                                reRunAdmin();

                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid ISBN!", "ERROR", JOptionPane.ERROR_MESSAGE);
                            reRunAdmin();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Author !", "ERROR", JOptionPane.ERROR_MESSAGE);
                        reRunAdmin();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Title!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    reRunAdmin();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Book Not Found");
                reRunAdmin();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid ISBN!", "ERROR", JOptionPane.ERROR_MESSAGE);
            reRunAdmin();
        }

        bookToEdit.setGenre(genre);
        bookToEdit.setAuthor(author);
        bookToEdit.setISBN(ISBN1);
        bookToEdit.setTitle(title);
        bookToEdit.setAvailability(true);

        JOptionPane.showMessageDialog(null, "Book edited SUCCESSFULLY");
        displayAllBooks(administrator.getAllBooks());

    }
    public  void displayAllBooks(List<Book> books) {

        String s="";
        String answer="All Books in the Library  \n\n";
        int count=1;
        for (Book book : books) {
            s="📖 "+ "BOOK"+count+": "+"\"Title\": (" + book.getTitle() + ")  \"Author\": (" + book.getAuthor() +
                    ")  \"ISBN\": (" + book.getISBN() + ")  \"Genre\": (" + book.getGenre() +
                    ")  \"Available\": (" + book.isAvailable()+")"+"\n\n";
            answer+=s;
            count++;
        }
        JOptionPane.showMessageDialog(null,answer);
    }


    public void run() {
        String[] options = {"Select--", "User", "Administrator"};
        String choice = (String) JOptionPane.showInputDialog(null,
                "Select an Option:",
                "Library Management System",
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[0]);
        if (choice == "Select--") {
            JOptionPane.showMessageDialog(null, "select a choice ", " ERROR", JOptionPane.ERROR_MESSAGE);
            run();
        }else if(choice == "User"){
            String[] optionsUser = {"Select--","Borrow Book", "Return Book", "List All Books", "Back to System"};
            String choiceUser = (String) JOptionPane.showInputDialog(null,
                    "Select an Option:",
                    "User Interface",
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    optionsUser,
                    optionsUser[0]);
            if (choiceUser == "Select--") {
                JOptionPane.showMessageDialog(null, "select a choice ", " ERROR", JOptionPane.ERROR_MESSAGE);
            } else if(choiceUser == "Borrow Book"){
                borrowBook();
            }else if (choiceUser == "Return Book") {
                ReturnBook();
                //displayAllBooks(library.getAllBooks());
            } else if (choiceUser == "List All Books") {
                displayAllBooks(administrator.getAllBooks());
            }else if(choiceUser == "Back to System"){
                run();
            }
            else {
                System.exit(0);
            }
            reRunUser();
        } else if( choice == "Administrator"){
            String[] optionsAdmin = {"Select--","Add Book", "Edit Book", "Delete Book", "List All Books","Back to System"};
            String choiceAdmin = (String) JOptionPane.showInputDialog(null,
                    "Select an Option:",
                    "Administrator interface",
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    optionsAdmin,
                    optionsAdmin[0]);

            if (choiceAdmin == "Select--") {
                JOptionPane.showMessageDialog(null, "select a choice ", " ERROR", JOptionPane.ERROR_MESSAGE);
            }else if (choiceAdmin == "Add Book") {
                addBook();
                //  displayAllBooks(library.getAllBooks());
            } else if (choiceAdmin == "Edit Book") {
                editBook();
                //   displayAllBooks(library.getAllBooks());
            } else if (choiceAdmin == "Delete Book") {
                deleteBook();
                //  displayAllBooks(library.getAllBooks());
            } else if (choiceAdmin == "List All Books") {
                displayAllBooks(administrator.getAllBooks());
            }else if(choiceAdmin == "Back to System"){
                run();
            }
            else {
                System.exit(0);
            }
            reRunAdmin();
        }
    }
    public  void reRun(){
        String[] options = {"Select--","Add Book", "Edit Book", "Delete Book", "List All Books","Borrow Book","Return Book", "Exit"};
        String choice = (String) JOptionPane.showInputDialog(null,
                "Select an Option:",
                "Library Management System",
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[0]);
        if (choice == "Select--") {
            JOptionPane.showMessageDialog(null, "select a choice ", " ERROR", JOptionPane.ERROR_MESSAGE);
        }else if (choice == "Add Book") {
            addBook();
            //displayAllBooks(library.getAllBooks());
        } else if (choice == "Edit Book") {
            editBook();
            // displayAllBooks(library.getAllBooks());
        } else if (choice == "Delete Book") {
            deleteBook();
            //    displayAllBooks(library.getAllBooks());
        } else if (choice == "List All Books") {
            displayAllBooks(administrator.getAllBooks());
        } else if(choice == "Borrow Book"){
            borrowBook();
            //  displayAllBooks(library.getAllBooks());
        }else if (choice == "Return Book") {
            ReturnBook();
            //displayAllBooks(library.getAllBooks());
        }
        else {
            System.exit(0);
        }

        reRun();
    }
    public void reRunAdmin(){
        String[] optionsAdmin = {"Select--","Add Book", "Edit Book", "Delete Book", "List All Books","Back to System"};
        String choiceAdmin = (String) JOptionPane.showInputDialog(null,
                "Select an Option:",
                "Administrator Interface ",
                JOptionPane.DEFAULT_OPTION,
                null,
                optionsAdmin,
                optionsAdmin[0]);

        if (choiceAdmin == "Select--") {
            JOptionPane.showMessageDialog(null, "select a choice ", " ERROR", JOptionPane.ERROR_MESSAGE);
            reRunAdmin();
        }else if (choiceAdmin == "Add Book") {
            addBook();
            // displayAllBooks(library.getAllBooks());
        } else if (choiceAdmin == "Edit Book") {
            editBook();
            //displayAllBooks(library.getAllBooks());
        } else if (choiceAdmin == "Delete Book") {
            deleteBook();
            //displayAllBooks(library.getAllBooks());
        } else if (choiceAdmin == "List All Books") {
            displayAllBooks(administrator.getAllBooks());
        }else if(choiceAdmin == "Back to System"){
            run();
        } else {
            System.exit(0);
        }
        reRunAdmin();
    }

    public void reRunUser(){
        String[] optionsUser = {"Select--","Borrow Book", "Return Book", "List All Books", "Back to System"};
        String choiceUser = (String) JOptionPane.showInputDialog(null,
                "Select an Option:",
                "User Interface",
                JOptionPane.DEFAULT_OPTION,
                null,
                optionsUser,
                optionsUser[0]);
        if (choiceUser == "Select--") {
            JOptionPane.showMessageDialog(null, "select a choice ", " ERROR", JOptionPane.ERROR_MESSAGE);
        } else if(choiceUser == "Borrow Book"){
            borrowBook();
            //  displayAllBooks(library.getAllBooks());
        }else if (choiceUser == "Return Book") {
            ReturnBook();
            //displayAllBooks(library.getAllBooks());
        } else if (choiceUser == "List All Books") {
            displayAllBooks(administrator.getAllBooks());
        }else if(choiceUser == "Back to System"){
            run();
        }
        else {
            System.exit(0);
        }
        reRunUser();
    }
    public boolean containsNumbers(String input) {
        // Loop through each character in the string
        for (char c : input.toCharArray()) {
            // Check if the character is a digit
            if (Character.isDigit(c)) {
                return false; // String contains a number, return false
            }
        }
        return true; // String does not contain any numbers
    }

    public boolean containsCharacters(String input) {
        for (char c : input.toCharArray()) {
            // Check if the character is a digit
            if (Character.isLetter(c)) {
                return false; // String contains a Character, return false
            }
        }
        return true; // String does not contain any Characters
    }
    public boolean hasNoSpecialCharacters(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
    public boolean validateElevenDigits(String input) {
        return input != null && input.length() == 11;
    }
    public boolean repeatedISBN(String ISBN){
        for (int i = 0; i < administrator.getAllBooks().size(); i++) {
            Book book = administrator.getAllBooks().get(i);
            if (book.getISBN().equals(ISBN)) {
                return true;
            }
        }
        return false;
    }
    public Book findBookByISBN(String ISBN) {

        if (ISBN != null && !ISBN.isEmpty()&&!ISBN.isBlank() && hasNoSpecialCharacters(ISBN)) {
            for (int i = 0; i < administrator.getAllBooks().size(); i++) { //as a private Arraylist of books we have to call a get method
                Book book = administrator.getAllBooks().get(i);
                if (book.getISBN().equals(ISBN)) {
                    return book;
                }
            }
        }
        return null;
    }
    public Book FindBookByTitle(String title) {

        if (title != null &&!title.isBlank() && !title.isEmpty()&& hasNoSpecialCharacters(title)) {
            for (int i = 0; i < administrator.getAllBooks().size(); i++) { //as a private Arraylist of books we have to call a get method
                Book book = administrator.getAllBooks().get(i);
                if (book.getTitle().equals(title)) {
                    return book;
                }
            }
        }
        return null;

    }
}

