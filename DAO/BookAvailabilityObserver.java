package DAO;

import DTO.Book;

public interface BookAvailabilityObserver {
    void updateAvailability (Book book);
}
