package controllers;

import factories.*;
import models.BorrowingRecord;
import singleton.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class BorrowingRecordController
{

    // Constructor

    public BorrowingRecordController()
    {
        database = Database.getInstance();
    }

    // Properties

    private final Database database;

    // Accessor Methods: Borrowing Record

    /**
     * Retrieves all borrowing records from the database.
     *
     * @return an ArrayList of BorrowingRecord objects
     */
    public ArrayList<BorrowingRecord> getBorrowingRecordsByAll()
    {
        return queryBorrowingRecords("SELECT * FROM borrowing_records");
    }

    /**
     * Retrieves a borrowing record from the database by its unique identifier.
     *
     * @param patronId the ID of the patron
     * @return the BorrowingRecord object, or null if not found
     */
    public ArrayList<BorrowingRecord> getBorrowingRecordsByPatronId(String patronId)
    {
        return queryBorrowingRecords("SELECT * FROM borrowing_records WHERE patron_id = ?", patronId);
    }

    /**
     * Retrieves a borrowing record from the database by its unique identifier.
     *
     * @param id the ID of the borrowing record
     * @return the BorrowingRecord object, or null if not found
     */
    public BorrowingRecord getBorrowingRecordById(String id)
    {
        ArrayList<BorrowingRecord> borrowingRecords = queryBorrowingRecords("SELECT * FROM borrowing_records WHERE id = ?", id);
        return borrowingRecords.isEmpty() ? null : borrowingRecords.get(0);
    }

    /**
     * Retrieves the latest borrowing record added to the database.
     *
     * @return the BorrowingRecord object, or default value if not found
     */
    public BorrowingRecord getLatestBorrowingRecord()
    {
        BorrowingRecordFactory borrowingRecordFactory = new BorrowingRecordFactory();
        ArrayList<BorrowingRecord> borrowingRecords = queryBorrowingRecords("SELECT * FROM borrowing_records ORDER BY id DESC LIMIT 1");
        return borrowingRecords.isEmpty() ? borrowingRecordFactory.createBorrowingRecord() : borrowingRecords.get(0);
    }

    /**
     * Adds a new borrowing record to the database.
     *
     * @param borrowingRecord the BorrowingRecord object to add
     */
    public boolean addBorrowingRecord(BorrowingRecord borrowingRecord)
    {
        int rowsAffected = updateBorrowingRecords("INSERT INTO borrowing_records " +
                        "(id, book_id, patron_id, librarian_id, borrowing_date, due_date, return_date, request_state, record_status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                borrowingRecord.getId(),
                borrowingRecord.getBookId(),
                borrowingRecord.getPatronId(),
                borrowingRecord.getLibrarianId(),
                new java.sql.Date(borrowingRecord.getBorrowingDate().getTime()),
                new java.sql.Date(borrowingRecord.getDueDate().getTime()),
                new java.sql.Date(borrowingRecord.getReturnDate().getTime()),
                borrowingRecord.getRequestState(),
                borrowingRecord.getRecordStatus());
        return rowsAffected > 0;
    }

    /**
     * Updates an existing borrowing record in the database.
     *
     * @param borrowingRecord the BorrowingRecord object to update
     */
    public boolean updateBorrowingRecord(BorrowingRecord borrowingRecord)
    {
        int rowsAffected = updateBorrowingRecords("UPDATE borrowing_records " +
                        "SET book_id = ?, patron_id = ?, librarian_id = ?, borrowing_date = ?, due_date = ?, return_date = ?, request_state = ?, record_status = ? " +
                        "WHERE id = ?",
                borrowingRecord.getBookId(),
                borrowingRecord.getPatronId(),
                borrowingRecord.getLibrarianId(),
                new java.sql.Date(borrowingRecord.getBorrowingDate().getTime()),
                new java.sql.Date(borrowingRecord.getDueDate().getTime()),
                new java.sql.Date(borrowingRecord.getReturnDate().getTime()),
                borrowingRecord.getRequestState(),
                borrowingRecord.getRecordStatus(),
                borrowingRecord.getId());
        return rowsAffected > 0;
    }

    // Utilities

    private ArrayList<BorrowingRecord> queryBorrowingRecords(String query, Object... values)
    {
        ArrayList<BorrowingRecord> borrowingRecords = new ArrayList<>();

        try (PreparedStatement statement = database.prepareStatement(query, values))
        {
            ResultSet results = statement.executeQuery();

            borrowingRecords.addAll(convertBorrowingRecordsByResultSet(results));
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return borrowingRecords;
    }

    private int updateBorrowingRecords(String query, Object... values)
    {
        int rowsAffected = 0;

        try (PreparedStatement statement = database.prepareStatement(query, values))
        {
            rowsAffected = statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return rowsAffected;
    }

    private ArrayList<BorrowingRecord> convertBorrowingRecordsByResultSet(ResultSet resultSet)
    {
        BorrowingRecordFactory borrowingRecordFactory = new BorrowingRecordFactory();
        ArrayList<BorrowingRecord> borrowingRecords = new ArrayList<>();

        try
        {
            while (resultSet.next())
            {
                BorrowingRecord borrowingRecord = borrowingRecordFactory.createBorrowingRecord(
                        resultSet.getString("id"),
                        resultSet.getString("book_id"),
                        resultSet.getString("patron_id"),
                        resultSet.getString("librarian_id"),
                        resultSet.getString("record_status"),
                        resultSet.getDate("borrowing_date"),
                        resultSet.getDate("due_date"),
                        resultSet.getInt("request_state"),
                        resultSet.getDate("return_date")
                );

                borrowingRecords.add(borrowingRecord);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return borrowingRecords;
    }

}
