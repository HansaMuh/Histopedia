package controllers;

import factories.BiographyBookFactory;
import factories.BookFactory;
import factories.MilitaryHistoryBookFactory;
import factories.RevolutionBookFactory;
import models.BiographyBook;
import models.Book;
import models.User;
import singleton.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookController
{

    // Constructor

    public BookController()
    {
        database = Database.getInstance();
    }

    // Properties

    private final Database database;

    // Accessor Methods: Book

    /**
     * Retrieves all books from the database.
     *
     * @return an ArrayList of Book objects
     */
    public ArrayList<Book> getBooksByAll()
    {
        return queryBooks("SELECT * FROM books");
    }

    /**
     * Retrieves books from the database by their title.
     *
     * @param title the title of the book
     * @return an ArrayList of Book objects
     */
    public ArrayList<Book> getBooksByTitle(String title)
    {
        return queryBooks("SELECT * FROM books WHERE title LIKE ?", title);
    }

    /**
     * Retrieves a list of books that match the specified genre.
     *
     * @param genre The genre to search for.
     * @return An ArrayList of Book objects that match the specified genre.
     */
    public ArrayList<Book> getBooksByGenre(String genre)
    {
        return queryBooks("SELECT * FROM books WHERE genre LIKE ?", genre);
    }

    /**
     * Retrieves a list of books that match the specified author.
     *
     * @param author The author to search for.
     * @return An ArrayList of Book objects that match the specified author.
     */
    public ArrayList<Book> getBooksByAuthor(String author)
    {
        return queryBooks("SELECT * FROM books WHERE author LIKE ?", author);
    }

    /**
     * Retrieves a list of books borrowed by a specific patron.
     *
     * @param patronId The ID of the patron.
     * @return An ArrayList of Book objects borrowed by the specified patron.
     */
    public ArrayList<Book> getBorrowedBooksByPatronId(String patronId)
    {
        return queryBooks("SELECT books.* FROM books " +
                        "JOIN borrowing_records ON books.id = borrowing_records.book_id " +
                        "WHERE borrowing_records.patron_id = ? AND borrowing_records.record_status = ?",
                patronId,
                "Active");
    }

    /**
     * Retrieves a list of books borrowed by a specific patron.
     *
     * @param emailAddress The email address of the patron.
     * @return An ArrayList of Book objects borrowed by the specified patron.
     */
    public ArrayList<Book> getBorrowedBooksByPatronEmail(String emailAddress)
    {
        return queryBooks("SELECT books.* FROM books " +
                "JOIN borrowing_records ON books.id = borrowing_records.book_id " +
                "JOIN users ON borrowing_records.patron_id = users.id " +
                "WHERE users.email_address = ? AND borrowing_records.record_status = ?",
                emailAddress,
                "Active");
    }

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param id The unique identifier of the book.
     * @return The Book object corresponding to the specified id, or null if not found.
     */
    public Book getBookById(String id)
    {
        ArrayList<Book> books = queryBooks("SELECT * FROM books WHERE id = ?", id);
        return books.isEmpty() ? null : books.get(0);
    }

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn The ISBN of the book.
     * @return The Book object corresponding to the specified ISBN, or null if not found.
     */
    public Book getBookByIsbn(String isbn)
    {
        ArrayList<Book> books = queryBooks("SELECT * FROM books WHERE isbn = ?", isbn);
        return books.isEmpty() ? null : books.get(0);
    }

    /**
     * Retrieves the latest book added to the database.
     *
     * @return The Book object corresponding to the latest book added to the database.
     */
    public Book getLatestBook()
    {
        BiographyBookFactory biographyBookFactory = new BiographyBookFactory();
        ArrayList<Book> books = queryBooks("SELECT * FROM books ORDER BY id DESC LIMIT 1");
        return books.isEmpty() ? biographyBookFactory.createBook() : books.get(0);
    }

    /**
     * Adds a new book to the database.
     *
     * @param book The Book object to be added.
     */
    public boolean addBook(Book book)
    {
        int rowsAffected = updateBooks("INSERT INTO books (id, title, genre, description, author, publisher, publication_year, isbn, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                book.getDescription(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublicationYear(),
                book.getIsbn(),
                book.getStatus());
        return rowsAffected > 0;
    }

    /**
     * Updates an existing book in the database.
     *
     * @param book The Book object with updated information.
     */
    public boolean updateBook(Book book)
    {
        int rowsAffected = updateBooks("UPDATE books SET title = ?, genre = ?, description = ?, author = ?, publisher = ?, publication_year = ?, isbn = ?, status = ? WHERE id = ?",
                book.getTitle(),
                book.getGenre(),
                book.getDescription(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublicationYear(),
                book.getIsbn(),
                book.getStatus(),
                book.getId());
        return rowsAffected > 0;
    }

    /**
     * Deletes a book from the database by its unique identifier.
     *
     * @param id The unique identifier of the book to be deleted.
     */
    public boolean deleteBook(String id)
    {
        int rowsAffected = updateBooks("DELETE FROM books WHERE id = ?", id);
        return rowsAffected > 0;
    }

    // Utilities

    private ArrayList<Book> queryBooks(String query, Object... values)
    {
        ArrayList<Book> books = new ArrayList<Book>();

        try (PreparedStatement statement = database.prepareStatement(query, values))
        {
            ResultSet results = statement.executeQuery();

            books.addAll(convertBooksByResultSet(results));
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return books;
    }

    private int updateBooks(String query, Object... values)
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

    private ArrayList<Book> convertBooksByResultSet(ResultSet resultSet)
    {
        BookFactory bookFactory = null;
        ArrayList<Book> books = new ArrayList<Book>();

        try
        {
            while (resultSet.next())
            {
                switch (resultSet.getString("genre"))
                {
                    case "Biography":
                        bookFactory = new BiographyBookFactory();
                        break;
                    case "Military History":
                        bookFactory = new MilitaryHistoryBookFactory();
                        break;
                    case "Revolution":
                        bookFactory = new RevolutionBookFactory();
                        break;
                }

                Book book = bookFactory.createBook(
                        resultSet.getString("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getString("description"),
                        resultSet.getString("author"),
                        resultSet.getString("publisher"),
                        resultSet.getInt("publication_year"),
                        resultSet.getString("isbn"),
                        resultSet.getString("status")
                );

                books.add(book);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return books;
    }

}
