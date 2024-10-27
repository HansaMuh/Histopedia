package models;

import java.util.*;

/**
 * Class representing a borrowing record in the library system.
 */
public class BorrowingRecord
{

    // Constructor

    /**
     * Constructs a new instance of the BorrowingRecord class.
     *
     * @param id The unique identifier of the borrowing record.
     * @param bookId The unique identifier of the book being borrowed.
     * @param patronId The unique identifier of the patron borrowing the book.
     * @param librarianId The unique identifier of the librarian processing the transaction.
     * @param borrowingDate The date when the book was borrowed.
     * @param dueDate The date when the book is due to be returned.
     * @param returnDate The date when the book was actually returned.
     * @param requestState The request state of the borrowing record.
     * @param recordStatus The current status of the borrowing record.
     */
    public BorrowingRecord(String id, String bookId, String patronId, String librarianId, String recordStatus, Date borrowingDate, Date dueDate, int requestState, Date returnDate)
    {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.librarianId = librarianId;
        this.borrowingDate = borrowingDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.requestState = requestState;
        this.recordStatus = recordStatus;
    }

    // Properties

    private final String id;
    private final String bookId;
    private final String patronId;
    private String librarianId;
    private final Date borrowingDate;
    private final Date dueDate;
    private Date returnDate;
    private int requestState; // States: -1 = NULL, 0 = Rejected, 1 = Accepted
    private String recordStatus; // Statuses: Active, Returned, Returned Overdue, Overdue

    // Getters

    /**
     * Gets the unique identifier of the borrowing record.
     *
     * @return The unique identifier of the borrowing record.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Gets the unique identifier of the book being borrowed.
     *
     * @return The unique identifier of the book being borrowed.
     */
    public String getBookId()
    {
        return this.bookId;
    }

    /**
     * Gets the unique identifier of the patron borrowing the book.
     *
     * @return The unique identifier of the patron borrowing the book.
     */
    public String getPatronId()
    {
        return this.patronId;
    }

    /**
     * Gets the unique identifier of the librarian processing the transaction.
     *
     * @return The unique identifier of the librarian processing the transaction.
     */
    public String getLibrarianId()
    {
        return this.librarianId;
    }

    /**
     * Gets the date when the book was borrowed.
     *
     * @return The date when the book was borrowed.
     */
    public Date getBorrowingDate()
    {
        return this.borrowingDate;
    }

    /**
     * Gets the date when the book is due to be returned.
     *
     * @return The date when the book is due to be returned.
     */
    public Date getDueDate()
    {
        return this.dueDate;
    }

    /**
     * Gets the date when the book was actually returned.
     *
     * @return The date when the book was actually returned.
     */
    public Date getReturnDate()
    {
        return this.returnDate;
    }

    /**
     * Gets the request state of the borrowing record.
     *
     * @return The request state of the borrowing record.
     */
    public int getRequestState()
    {
        return this.requestState;
    }

    /**
     * Gets the current status of the borrowing record.
     *
     * @return The current status of the borrowing record.
     */
    public String getRecordStatus()
    {
        return this.recordStatus;
    }

    // Setters

    /**
     * Sets the unique identifier of the librarian processing the transaction.
     *
     * @param librarianId The unique identifier of the librarian processing the transaction.
     */
    public void setLibrarianId(String librarianId)
    {
        this.librarianId = librarianId;
    }

    /**
     * Sets the date when the book was actually returned.
     *
     * @param returnDate The date when the book was actually returned.
     */
    public void setReturnDate(Date returnDate)
    {
        this.returnDate = returnDate;
    }

    /**
     * Sets the request state of the borrowing record.
     *
     * @param requestState The request state of the borrowing record.
     */
    public void setRequestState(int requestState)
    {
        this.requestState = requestState;
    }

    /**
     * Sets the current status of the borrowing record.
     *
     * @param recordStatus The current status of the borrowing record.
     */
    public void setRecordStatus(String recordStatus)
    {
        this.recordStatus = recordStatus;
    }

    // Methods

    /**
     * Displays the public details of the borrowing record.
     */
    public void displayPublicDetails()
    {
        System.out.println("[Borrowing Record Details #" + this.id + "]");
        System.out.println("    Book ID: " + this.bookId);
        System.out.println("    Patron ID: " + this.patronId);
        System.out.println("    Librarian ID: " + this.librarianId);
        System.out.println("    Borrowing Date: " + this.borrowingDate);
        System.out.println("    Due Date: " + this.dueDate);
        System.out.println("    Return Date: " + this.returnDate);
        System.out.println("    Request State: " + this.requestState);
        System.out.println("    Record Status: " + this.recordStatus);
    }

}