package DTO;

public class Book {
    private String title;
    private String author;
    private String  ISBN;
    private String genre;
    private boolean available;
    private User borrowedBy;
    public Book (){

    }
    public Book( String title, String author, String ISBN , String genre ){
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.genre = genre;
        this.available = true;  //the Book is available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public User getBorrowedBy(){
        return borrowedBy;
    }
    public void setBorrowedBy(User borrowedBy){
        this.borrowedBy = borrowedBy;
    }
    public void setAvailability(boolean availability) {
        this.available = availability;
    }
}
