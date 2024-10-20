package factories;

import models.*;

/**
 * The BookFactory abstract class is responsible for creating new Book instances.
 */
public abstract class BookFactory
{

    // Methods

    /**
     * Creates a new Book instance.
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
     * @return A new Book instance.
     */
    public abstract Book createBook(String id, String title, String genre, String description, String author, String publisher, int publicationYear, String isbn, String status);

}
