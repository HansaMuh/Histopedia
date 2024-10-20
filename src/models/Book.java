package models;

/**
 * Abstract class representing a Book.
 */
public abstract class Book {

    // Constructor

    /**
     * Constructs a new Book instance.
     *
     * @param id The unique identifier for the book.
     * @param title The title of the book.
     * @param genre The genre of the book.
     * @param description A brief description of the book.
     * @param author The author of the book.
     * @param publisher The publisher of the book.
     * @param publicationYear The year the book was published.
     * @param isbn The International Standard Book Number of the book.
     * @param status The current status of the book (e.g., Available, Borrowed, Unavailable).
     */
    public Book(String id, String title, String genre, String description, String author, String publisher, int publicationYear, String isbn, String status)
    {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.status = status;
    }

    // Properties

    private final String id;
    private String title;
    private String genre;
    private String description;
    private String author;
    private String publisher;
    private int publicationYear;
    private String isbn;
    private String status; // Statuses: Available, Borrowed, Unavailable

    // Getters

    /**
     * Gets the unique identifier of the book.
     *
     * @return The unique identifier of the book.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Gets the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * Gets the genre of the book.
     *
     * @return The genre of the book.
     */
    public String getGenre()
    {
        return this.genre;
    }

    /**
     * Gets the description of the book.
     *
     * @return The description of the book.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the author of the book.
     *
     * @return The author of the book.
     */
    public String getAuthor()
    {
        return this.author;
    }

    /**
     * Gets the publisher of the book.
     *
     * @return The publisher of the book.
     */
    public String getPublisher()
    {
        return this.publisher;
    }

    /**
     * Gets the publication year of the book.
     *
     * @return The publication year of the book.
     */
    public int getPublicationYear()
    {
        return this.publicationYear;
    }

    /**
     * Gets the ISBN of the book.
     *
     * @return The ISBN of the book.
     */
    public String getIsbn()
    {
        return this.isbn;
    }

    /**
     * Gets the current status of the book.
     *
     * @return The current status of the book.
     */
    public String getStatus()
    {
        return this.status;
    }

    // Setters

    /**
     * Sets the title of the book.
     *
     * @param title The title of the book.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Sets the genre of the book.
     *
     * @param genre The genre of the book.
     */
    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    /**
     * Sets the description of the book.
     *
     * @param description The description of the book.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the author of the book.
     *
     * @param author The author of the book.
     */
    public void setAuthor(String author)
    {
        this.author = author;
    }

    /**
     * Sets the publisher of the book.
     *
     * @param publisher The publisher of the book.
     */
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    /**
     * Sets the publication year of the book.
     *
     * @param publicationYear The publication year of the book.
     */
    public void setPublicationYear(int publicationYear)
    {
        this.publicationYear = publicationYear;
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param isbn The ISBN of the book.
     */
    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    /**
     * Sets the status of the book.
     *
     * @param status The status of the book.
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    // Methods

    /**
     * Displays the header information of the book.
     * This method must be implemented by subclasses.
     */
    public abstract void displayHeader();

    /**
     * Displays the detailed information of the book.
     * This method must be implemented by subclasses.
     */
    public abstract void displayDetails();

}