package singleton;

import factories.*;
import models.*;

import java.sql.*;
import java.util.*;

/**
 * The Database class implements the Singleton design pattern to manage a single
 * instance of a database connection. It provides methods to get the instance
 * and the connection to the database.
 */
public class Database
{

    // Constructor

    /**
     * Private constructor to initialize the database connection.
     *
     * @param username the username for the database connection
     * @param password the password for the database connection
     */
    private Database(String username, String password)
    {
        this.username = username;
        this.password = password;

        connect();
    }

    // Static Fields

    /**
     * The single instance of the Database class.
     */
    private static Database instance;

    // Fields

    /**
     * The port number for the database connection.
     */
    private final String PORT = "3306";
    /**
     * The header for the database URL.
     */
    private final String HEADER = "Histopedia";
    /**
     * The URL for the database connection.
     */
    private final String URL = "jdbc:mysql://localhost:" + PORT + "/" + HEADER;

    // Properties

    /**
     * The connection object for the database.
     */
    private Connection connection;
    /**
     * The username for the database connection.
     */
    private String username;
    /**
     * The password for the database connection.
     */
    private String password;

    // Utilities

    /**
     * Establishes the connection to the database using the provided URL, username,
     * and password.
     */
    private void connect()
    {
        try
        {
            connection = DriverManager.getConnection(URL, username, password);
        }
        catch (SQLException ex)
        {
            System.out.println("Connection error detected.\nDetails:\n" + ex.getMessage());
        }
    }

    // Field Methods

    /**
     * Returns the single instance of the Database class, creating it if it does not
     * already exist.
     *
     * @return the single instance of the Database class
     */
    public static Database getInstance()
    {
        if (instance == null)
        {
            synchronized (Database.class)
            {
                if (instance == null)
                {
                    instance = new Database("root", "");
                }
            }
        }

        return instance;
    }

    // Accessor Methods: User

    /**
     * Retrieves all users from the database.
     *
     * @return an ArrayList of User objects
     */
    public ArrayList<User> getUsersByAll()
    {
        UserFactory patronFactory = new PatronFactory();
        UserFactory librarianFactory = new LibrarianFactory();
        ArrayList<User> users = new ArrayList<User>();

        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                int accessLevel = results.getInt("access_level");
                User user = null;

                if (accessLevel == 0)
                {
                    user = librarianFactory.createUser(results.getString("id"), results.getString("username"),
                            results.getString("email_address"), results.getString("password"));
                }
                else
                {
                    user = patronFactory.createUser(results.getString("id"), results.getString("username"),
                            results.getString("email_address"), results.getString("password"));
                }

                users.add(user);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return users;
    }

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param id the ID of the user
     * @return the User object, or null if not found
     */
    public User getUserById(String id)
    {
        UserFactory patronFactory = new PatronFactory();
        UserFactory librarianFactory = new LibrarianFactory();
        User user = null;

        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, id);

            ResultSet results = statement.executeQuery();

            if (results.next())
            {
                int accessLevel = results.getInt("access_level");

                if (accessLevel == 0)
                {
                    user = librarianFactory.createUser(results.getString("id"), results.getString("username"),
                            results.getString("email_address"), results.getString("password"));
                }
                else
                {
                    user = patronFactory.createUser(results.getString("id"), results.getString("username"),
                            results.getString("email_address"), results.getString("password"));
                }
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return user;
    }

    /**
     * Retrieves a user from the database by their login information.
     *
     * @param emailAddress the email address of the user
     * @param password the password of the user
     * @return the User object, or null if not found
     */
    public User getUserByLoginInfo(String emailAddress, String password)
    {
        UserFactory patronFactory = new PatronFactory();
        UserFactory librarianFactory = new LibrarianFactory();
        User user = null;

        String query = "SELECT * FROM users WHERE email_address = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, emailAddress);
            statement.setString(2, password);

            ResultSet results = statement.executeQuery();

            if (results.next())
            {
                int accessLevel = results.getInt("access_level");

                if (accessLevel == 0)
                {
                    user = librarianFactory.createUser(results.getString("id"), results.getString("username"),
                            results.getString("email_address"), results.getString("password"));
                }
                else
                {
                    user = patronFactory.createUser(results.getString("id"), results.getString("username"),
                            results.getString("email_address"), results.getString("password"));
                }
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return user;
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the User object to add
     */
    public void addUser(User user)
    {
        String query = "INSERT INTO users (id, access_level, username, email_address, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, user.getId());
            statement.setInt(2, user.getAccessLevel());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getEmailAddress());
            statement.setString(5, user.getPassword());

            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }
    }

    // Accessor Methods: Borrowing Record

    /**
     * Retrieves all borrowing records from the database.
     *
     * @return an ArrayList of BorrowingRecord objects
     */
    public ArrayList<BorrowingRecord> getBorrowingRecordsByAll()
    {
        ArrayList<BorrowingRecord> borrowingRecords = new ArrayList<BorrowingRecord>();

        String query = "SELECT * FROM borrowing_records";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                BorrowingRecord borrowingRecord = BorrowingRecordFactory.createBorrowingRecord(results.getString("id"),
                        results.getString("book_id"), results.getString("patron_id"), results.getString("librarian_id"),
                        results.getString("record_status"), results.getDate("borrowing_date"),
                        results.getDate("due_date"), results.getInt("request_state"), results.getDate("return_date"));

                borrowingRecords.add(borrowingRecord);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return borrowingRecords;
    }

    /**
     * Retrieves a borrowing record from the database by its unique identifier.
     *
     * @param patronId the ID of the patron
     * @return the BorrowingRecord object, or null if not found
     */
    public ArrayList<BorrowingRecord> getBorrowingRecordsByPatronId(String patronId)
    {
        ArrayList<BorrowingRecord> borrowingRecords = new ArrayList<BorrowingRecord>();

        String query = "SELECT * FROM borrowing_records WHERE patron_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, patronId);

            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                BorrowingRecord borrowingRecord = BorrowingRecordFactory.createBorrowingRecord(results.getString("id"),
                        results.getString("book_id"), results.getString("patron_id"), results.getString("librarian_id"),
                        results.getString("record_status"), results.getDate("borrowing_date"),
                        results.getDate("due_date"), results.getInt("request_state"), results.getDate("return_date"));

                borrowingRecords.add(borrowingRecord);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return borrowingRecords;
    }

    /**
     * Retrieves a borrowing record from the database by its unique identifier.
     *
     * @param id the ID of the borrowing record
     * @return the BorrowingRecord object, or null if not found
     */
    public BorrowingRecord getBorrowingRecordById(String id)
    {
        String query = "SELECT * FROM borrowing_records WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, id);

            ResultSet results = statement.executeQuery();

            if (results.next())
            {
                BorrowingRecord borrowingRecord = BorrowingRecordFactory.createBorrowingRecord(results.getString("id"),
                        results.getString("book_id"), results.getString("patron_id"), results.getString("librarian_id"),
                        results.getString("record_status"), results.getDate("borrowing_date"),
                        results.getDate("due_date"), results.getInt("request_state"), results.getDate("return_date"));

                return borrowingRecord;
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return null;
    }

    /**
     * Adds a new borrowing record to the database.
     *
     * @param borrowingRecord the BorrowingRecord object to add
     */
    public void addBorrowingRecord(BorrowingRecord borrowingRecord)
    {
        String query = "INSERT INTO borrowing_records (id, book_id, patron_id, librarian_id, borrowing_date, " +
                "due_date, return_date, request_state, record_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, borrowingRecord.getId());
            statement.setString(2, borrowingRecord.getBookId());
            statement.setString(3, borrowingRecord.getPatronId());
            statement.setString(4, borrowingRecord.getLibrarianId());
            statement.setDate(5, new java.sql.Date(borrowingRecord.getBorrowingDate().getTime()));
            statement.setDate(6, new java.sql.Date(borrowingRecord.getDueDate().getTime()));
            statement.setDate(7, new java.sql.Date(borrowingRecord.getReturnDate().getTime()));
            statement.setInt(8, borrowingRecord.getRequestState());
            statement.setString(9, borrowingRecord.getRecordStatus());

            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }
    }

    /**
     * Updates an existing borrowing record in the database.
     *
     * @param borrowingRecord the BorrowingRecord object to update
     */
    public void updateBorrowingRecord(BorrowingRecord borrowingRecord)
    {
        String query = "UPDATE borrowing_records SET librarian_id = ?, return_date = ?, request_state = ?, record_status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(3, borrowingRecord.getLibrarianId());
            statement.setDate(6, new java.sql.Date(borrowingRecord.getReturnDate().getTime()));
            statement.setInt(7, borrowingRecord.getRequestState());
            statement.setString(8, borrowingRecord.getRecordStatus());
            statement.setString(9, borrowingRecord.getId());

            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }
    }

    // Accessor Methods: Book

    /**
     * Retrieves all books from the database.
     *
     * @return an ArrayList of Book objects
     */
    public ArrayList<Book> getBooksByAll()
    {
        BookFactory bookFactory = null;
        ArrayList<Book> books = new ArrayList<Book>();

        String query = "SELECT * FROM books";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                switch (results.getString("genre"))
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

                Book book = bookFactory.createBook(results.getString("id"), results.getString("title"), results.getString(
                        "genre"),
                        results.getString("description"), results.getString("author"), results.getString("publisher"),
                        results.getInt("publication_year"), results.getString("isbn"), results.getString("status"));

                books.add(book);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return books;
    }

    /**
     * Retrieves books from the database by their title.
     *
     * @param title the title of the book
     * @return an ArrayList of Book objects
     */
    public ArrayList<Book> getBooksByTitle(String title)
    {
        BookFactory bookFactory = null;
        ArrayList<Book> books = new ArrayList<Book>();

        String query = "SELECT * FROM books WHERE title LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, title);

            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                switch (results.getString("genre"))
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

                Book book = bookFactory.createBook(results.getString("id"), results.getString("title"), results.getString(
                        "genre"),
                        results.getString("description"), results.getString("author"), results.getString("publisher"),
                        results.getInt("publication_year"), results.getString("isbn"), results.getString("status"));

                books.add(book);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return books;
    }

    /**
     * Retrieves a list of books that match the specified genre.
     *
     * @param genre The genre to search for.
     * @return An ArrayList of Book objects that match the specified genre.
     */
    public ArrayList<Book> getBooksByGenre(String genre)
    {
        BookFactory bookFactory = null;
        ArrayList<Book> books = new ArrayList<Book>();

        String query = "SELECT * FROM books WHERE genre LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, genre);

            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                switch (results.getString("genre"))
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

                Book book = bookFactory.createBook(results.getString("id"), results.getString("title"), results.getString(
                        "genre"),
                        results.getString("description"), results.getString("author"), results.getString("publisher"),
                        results.getInt("publication_year"), results.getString("isbn"), results.getString("status"));

                books.add(book);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return books;
    }

    /**
     * Retrieves a list of books that match the specified author.
     *
     * @param author The author to search for.
     * @return An ArrayList of Book objects that match the specified author.
     */
    public ArrayList<Book> getBooksByAuthor(String author)
    {
        BookFactory bookFactory = null;
        ArrayList<Book> books = new ArrayList<Book>();

        String query = "SELECT * FROM books WHERE author LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, author);

            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                switch (results.getString("genre"))
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

                Book book = bookFactory.createBook(results.getString("id"), results.getString("title"), results.getString(
                        "genre"),
                        results.getString("description"), results.getString("author"), results.getString("publisher"),
                        results.getInt("publication_year"), results.getString("isbn"), results.getString("status"));

                books.add(book);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return books;
    }

    /**
     * Retrieves a list of books borrowed by a specific patron.
     *
     * @param patronId The ID of the patron.
     * @return An ArrayList of Book objects borrowed by the specified patron.
     */
    public ArrayList<Book> getBorrowedBooksByPatronId(String patronId)
    {
        BookFactory bookFactory = null;
        ArrayList<Book> books = new ArrayList<Book>();

        String query = "SELECT books.* FROM books JOIN borrowing_records ON books.id = borrowing_records.book_id " +
                "WHERE borrowing_records.patron_id = ? AND borrowing_records.record_status = 'Active'";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, patronId);

            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                switch (results.getString("genre"))
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

                Book book = bookFactory.createBook(results.getString("id"), results.getString("title"), results.getString(
                        "genre"),
                        results.getString("description"), results.getString("author"), results.getString("publisher"),
                        results.getInt("publication_year"), results.getString("isbn"), results.getString("status"));

                books.add(book);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return books;
    }

    /**
     * Retrieves a list of books borrowed by a specific patron.
     *
     * @param emailAddress The email address of the patron.
     * @return An ArrayList of Book objects borrowed by the specified patron.
     */
    public ArrayList<Book> getBorrowedBooksByPatronEmail(String emailAddress)
    {
        BookFactory bookFactory = null;
        ArrayList<Book> books = new ArrayList<Book>();

        String query = "SELECT books.* FROM books JOIN borrowing_records ON books.id = borrowing_records.book_id " +
                "JOIN users ON borrowing_records.patron_id = users.id WHERE users.email_address = ? AND borrowing_records.record_status = 'Active'";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, emailAddress);

            ResultSet results = statement.executeQuery();

            while (results.next())
            {
                switch (results.getString("genre"))
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

                Book book = bookFactory.createBook(results.getString("id"), results.getString("title"), results.getString(
                        "genre"),
                        results.getString("description"), results.getString("author"), results.getString("publisher"),
                        results.getInt("publication_year"), results.getString("isbn"), results.getString("status"));

                books.add(book);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return books;
    }

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param id The unique identifier of the book.
     * @return The Book object corresponding to the specified id, or null if not found.
     */
    public Book getBookById(String id)
    {
        BookFactory bookFactory = null;
        Book book = null;

        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, id);

            ResultSet results = statement.executeQuery();

            if (results.next())
            {
                switch (results.getString("genre"))
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

                book = bookFactory.createBook(results.getString("id"), results.getString("title"), results.getString(
                        "genre"),
                        results.getString("description"), results.getString("author"), results.getString("publisher"),
                        results.getInt("publication_year"), results.getString("isbn"), results.getString("status"));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return book;
    }

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn The ISBN of the book.
     * @return The Book object corresponding to the specified ISBN, or null if not found.
     */
    public Book getBookByIsbn(String isbn)
    {
        BookFactory bookFactory = null;
        Book book = null;

        String query = "SELECT * FROM books WHERE isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, isbn);

            ResultSet results = statement.executeQuery();

            if (results.next())
            {
                switch (results.getString("genre"))
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

                book = bookFactory.createBook(results.getString("id"), results.getString("title"), results.getString(
                        "genre"),
                        results.getString("description"), results.getString("author"), results.getString("publisher"),
                        results.getInt("publication_year"), results.getString("isbn"), results.getString("status"));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return book;
    }

    /**
     * Adds a new book to the database.
     *
     * @param book The Book object to be added.
     */
    public void addBook(Book book)
    {
        String query = "INSERT INTO books (id, title, genre, description, author, publisher, publication_year, isbn, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getDescription());
            statement.setString(5, book.getAuthor());
            statement.setString(6, book.getPublisher());
            statement.setInt(7, book.getPublicationYear());
            statement.setString(8, book.getIsbn());
            statement.setString(9, book.getStatus());

            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }
    }

    /**
     * Updates an existing book in the database.
     *
     * @param book The Book object with updated information.
     */
    public void updateBook(Book book)
    {
        String query = "UPDATE books SET title = ?, genre = ?, description = ?, author = ?, publisher = ?, publication_year = ?, isbn = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getGenre());
            statement.setString(3, book.getDescription());
            statement.setString(4, book.getAuthor());
            statement.setString(5, book.getPublisher());
            statement.setInt(6, book.getPublicationYear());
            statement.setString(7, book.getIsbn());
            statement.setString(8, book.getStatus());
            statement.setString(9, book.getId());

            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }
    }

    /**
     * Deletes a book from the database by its unique identifier.
     *
     * @param id The unique identifier of the book to be deleted.
     */
    public void deleteBook(String id)
    {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, id);

            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }
    }

}