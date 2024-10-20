package factories;

import models.*;

import java.util.*;

/**
 * The BorrowingRecordFactory class is responsible for creating new BorrowingRecord instances.
 */
public class BorrowingRecordFactory
{

    // Methods

    /**
     * Creates a new borrowing record.
     *
     * @param id The unique identifier for the borrowing record.
     * @param bookId The unique identifier for the book being borrowed.
     * @param patronId The unique identifier for the patron borrowing the book.
     * @param librarianId The unique identifier for the librarian processing the transaction.
     * @param recordStatus The current status of the borrowing record (e.g., Active, Returned, Returned Overdue, Overdue).
     * @param borrowingDate The date when the book was borrowed.
     * @param dueDate The date when the book is due to be returned.
     * @param requestState The request state of the borrowing record.
     * @param returnDate The date when the book was actually returned.
     * @return The borrowing record.
     */
    public static BorrowingRecord createBorrowingRecord(String id, String bookId, String patronId, String librarianId, String recordStatus, Date borrowingDate, Date dueDate, int requestState, Date returnDate)
    {
        return new BorrowingRecord(id, bookId, patronId, librarianId, recordStatus, borrowingDate, dueDate, requestState, returnDate);
    }

}
