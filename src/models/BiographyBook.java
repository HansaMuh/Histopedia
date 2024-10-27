package models;

public class BiographyBook extends Book
{

    // Constructor

    /**
     * Constructs a new BiographyBook instance.
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
    public BiographyBook(String id, String title, String genre, String description, String author, String publisher, int publicationYear, String isbn, String status)
    {
        super(id, title, genre, description, author, publisher, publicationYear, isbn, status);
    }

    // Methods

    /**
     * Displays the header information of the biography book.
     * This method overrides the abstract method in the Book class.
     */
    @Override
    public void displayHeader()
    {
        System.out.println("[Biography Book]");
    }

    /**
     * Displays the detailed information of the biography book.
     * This method overrides the abstract method in the Book class.
     */
    @Override
    public void displayDetails()
    {
        displayHeader();
        System.out.println("    ID: " + this.getId() + "\n" +
                "   Title: " + this.getTitle() + "\n" +
                "   Genre: " + this.getGenre() + "\n" +
                "   Description: " + this.getDescription() + "\n" +
                "   Author: " + this.getAuthor() + "\n" +
                "   Publisher: " + this.getPublisher() + "\n" +
                "   Publication Year: " + this.getPublicationYear() + "\n" +
                "   ISBN: " + this.getIsbn() + "\n" +
                "   Status: " + this.getStatus());
    }

}
