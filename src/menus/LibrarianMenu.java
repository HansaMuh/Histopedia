package menus;

import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import factories.BiographyBookFactory;
import factories.MilitaryHistoryBookFactory;
import factories.RevolutionBookFactory;
import models.*;
import singleton.Database;

/**
 * The LibrarianMenu class extends the Menu class.
 */
public class LibrarianMenu extends Menu
{

    // Constructor

    /**
     * Constructs a new LibrarianMenu object and displays the menu.
     */
    public LibrarianMenu(User user)
    {
        super();

        currentUser = user;

        showMenu();
    }

    // Properties

    /**
     * The current user of the application.
     */
    private User currentUser;

    // Fields

    /**
     * The content of the authentication menu.
     */
    private final String MENU_CONTENT = "Welcome to Histopedia, " + currentUser.getUsername() + "! You're currently logged in as a Librarian.\n" +
            "1. View books\n" +
            "2. Browse books by title / genre / author\n" +
            "3. Add book\n" +
            "4. Edit book\n" +
            "5. Remove book\n" +
            "6. Update borrowing record status\n" +
            "Enter your choice ('0' to log out) >> ";
    /**
     * Constant representing the menu option to log out.
     */
    private final int LOGOUT = 0;
    /**
     * Constant representing the menu option to view books.
     */
    private final int VIEW_BOOKS = 1;
    /**
     * Constant representing the menu option to browse books.
     */
    private final int BROWSE_BOOKS = 2;
    /**
     * Constant representing the menu option to add a book.
     */
    private final int ADD_BOOK = 3;
    /**
     * Constant representing the menu option to edit a book.
     */
    private final int EDIT_BOOK = 4;
    /**
     * Constant representing the menu option to remove a book.
     */
    private final int REMOVE_BOOK = 5;
    /**
     * Constant representing the menu option to update a borrowing record status.
     */
    private final int UPDATE_BORROWING_RECORD_STATUS = 6;

    // Methods

    /**
     * Logs out the current user.
     */
    private void logout()
    {
        // TODO: Implement logout process
        isRunning = false;

        System.out.println("Logged out successfully.");
        System.out.println("Press enter to continue...");
        menuScanner.nextLine();

        new AuthenticationMenu();
    }

    /**
     * Displays all books in the library.
     */
    private void viewBooks()
    {
        // TODO: Implement viewBooks method
        ArrayList<Book> books = bookController.getBooksByAll();

        displayBooks(books);

        System.out.println();
        System.out.println("Press enter to continue...");
        menuScanner.nextLine();
    }

    /**
     * Browses books by title, genre, or author.
     */
    private void browseBooks()
    {
        // TODO: Implement browseBooks method
        int type = 0;
        while (type < 1 || type > 3)
        {
            System.out.print("Enter the type of search (1 -> Title, 2 -> Genre, 3 -> Author): ");
            type = Integer.parseInt(menuScanner.nextLine());
        }

        String keyword = "";
        while (keyword.isEmpty())
        {
            System.out.print("Enter the keyword to search for: ");
            keyword = menuScanner.nextLine();
        }

        ArrayList<Book> books = new ArrayList<>();
        switch (type)
        {
            case 1:
                books = bookController.getBooksByTitle(keyword);
                break;
            case 2:
                books = bookController.getBooksByGenre(keyword);
                break;
            case 3:
                books = bookController.getBooksByAuthor(keyword);
                break;
        }

        displayKeywordedBooks(books, keyword);

        System.out.println();
        System.out.println("Press enter to continue...");
        menuScanner.nextLine();
    }

    /**
     * Adds a new book to the library.
     */
    private void addBook()
    {
        String title;
        do {
            System.out.print("Enter book title (at least 5 characters): ");
            title = menuScanner.nextLine();
            if (title.length() < 5) {
                System.out.println("Title must be at least 5 characters long.");
            }
        } while (title.length() < 5);

        String genre;
        do {
            System.out.print("Enter book genre (Biography, Military History, Revolution): ");
            genre = menuScanner.nextLine().toLowerCase();
            if (!genre.equalsIgnoreCase("Biography") && !genre.equalsIgnoreCase("Military History") && !genre.equalsIgnoreCase("Revolution")) {
                System.out.println("Genre must be biography, military, or revolution.");
            }
        } while (!genre.equalsIgnoreCase("Biography") && !genre.equalsIgnoreCase("Military History") && !genre.equalsIgnoreCase("Revolution"));

        String description;
        do {
            System.out.print("Enter book description (at least 5 words): ");
            description = menuScanner.nextLine();
            if (description.split("\\s+").length < 5) {
                System.out.println("Description must contain at least 5 words.");
            }
        } while (description.split("\\s+").length < 5);

        int publicationYear;
        int currentYear = Year.now().getValue();
        do {
            System.out.print("Enter publication year (must be " + currentYear + " or later): ");
            publicationYear = menuScanner.nextInt();
            menuScanner.nextLine();
            if (publicationYear < currentYear) {
                System.out.println("Publication year must be " + currentYear + " or later.");
            }
        } while (publicationYear < currentYear);
	    
        String author;
        do {
        	System.out.print("Enter book author (at least 5 characters): ");
            author = menuScanner.nextLine();
            if(author.length()<5) {
            	System.out.println("Author must be at least 5 characters long.");
            }
        }while(author.length()<5);
	    
        String publisher;
        do {
        	System.out.print("Enter book publisher (at least 5 characters): ");
            publisher = menuScanner.nextLine();
            if(publisher.length()<5) {
            	System.out.println("Publisher must be at least 5 characters long.");
            }
        }while(publisher.length()<5);
	    
        System.out.print("Enter book ISBN: ");
        String isbn = menuScanner.nextLine();

        String id = generateBookId();
        String status = "Available";

        Book book = null;
        if(genre.equalsIgnoreCase("Biography")) {
        	book = new BiographyBookFactory().createBook(id, title, genre, description, author, publisher,
                    publicationYear, isbn,
                    status);
        }
        else if(genre.equalsIgnoreCase("Military History")) {
        	book = new MilitaryHistoryBookFactory().createBook(id, title, genre, description, author,
                    publisher,
                    publicationYear,
                    isbn, status);
        }
        else if(genre.equalsIgnoreCase("Revolution")) {
        	book = new RevolutionBookFactory().createBook(id, title, genre, description, author,
                    publisher, publicationYear, isbn,
                    status);
        }

        bookController.addBook(book);

        System.out.println("Book with ID " + book.getId() + " added successfully.");
        System.out.println("Press enter to continue...");
        menuScanner.nextLine();
    }

    /**
     * Edits an existing book in the library.
     */
    private void editBook()
    {
        // TODO: Implement editBook method
        displayBooks(bookController.getBooksByAll());

        String id = "";
        do {
            System.out.print("Enter the ID of the book to edit: ");
            id = menuScanner.nextLine();
            if (id.length() < 6) {
                System.out.println("ID must be at least 6 characters long.");
            }
        } while (id.length() < 6);

        Book book = bookController.getBookById(id);

        if (book == null)
        {
            System.out.println("Book not found with ID '" + id + "'.");
            System.out.println("Press enter to continue...");
            menuScanner.nextLine();
            return;
        }

        String title = "";
        while (title.length() < 5)
        {
            System.out.print("Enter new title (at least 5 characters, or '-' to skip): ");
            title = menuScanner.nextLine();

            if (title.equals("-"))
            {
                title = book.getTitle();
                break;
            }
        }

        String description = "";
        while (description.split("\\s+").length < 5)
        {
            System.out.print("Enter new description (at least 5 words, or '-' to skip): ");
            description = menuScanner.nextLine();

            if (description.equals("-"))
            {
                description = book.getDescription();
                break;
            }
        }

        int publicationYear = 0;
        int currentYear = Year.now().getValue();
        while (publicationYear < currentYear)
        {
            System.out.print("Enter new publication year (must be " + currentYear + " or later, or '-' to skip): ");
            String input = menuScanner.nextLine();

            if (input.equals("-"))
            {
                publicationYear = book.getPublicationYear();
                break;
            }

            try
            {
                publicationYear = Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                publicationYear = 0;
            }
        }

        String author = "";
        while (author.length() < 5)
        {
            System.out.print("Enter new author (at least 5 characters, or '-' to skip): ");
            author = menuScanner.nextLine();

            if (author.equals("-"))
            {
                author = book.getAuthor();
                break;
            }
        }

        String publisher = "";
        while (publisher.length() < 5)
        {
            System.out.print("Enter new publisher (at least 5 characters, or '-' to skip): ");
            publisher = menuScanner.nextLine();

            if (publisher.equals("-"))
            {
                publisher = book.getPublisher();
                break;
            }
        }

        String isbn = "";
        while (isbn.isEmpty())
        {
            System.out.print("Enter new ISBN ('-' to skip): ");
            isbn = menuScanner.nextLine();

            if (isbn.equals("-"))
            {
                isbn = book.getIsbn();
                break;
            }
        }

        book.setTitle(title);
        book.setDescription(description);
        book.setPublicationYear(publicationYear);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setIsbn(isbn);

        bookController.updateBook(book);

        System.out.println("Book with ID " + id + " has been updated.");
        System.out.println("Press enter to continue...");
        menuScanner.nextLine();
    }

    /**
     * Removes a book from the library.
     */
    private void removeBook()
    {
        displayBooks(bookController.getBooksByAll());

        String id = "";
        do {
            System.out.print("Enter the ID of the book to delete: ");
            id = menuScanner.nextLine();
            if (id.length() < 6) {
                System.out.println("ID must be at least 6 characters long.");
            }
        } while (id.length() < 6);

        boolean success = bookController.deleteBook(id);
        
        if(success) {
        	System.out.println("Book with ID " + id + " has been removed.");
        }else {
        	System.out.println("Book with ID " + id + " not found.");
        }
        System.out.println("Press enter to continue...");menuScanner.nextLine();
    }

    /**
     * Updates the status of a borrowing record.
     */
    private void updateBorrowingRecordStatus()
    {
        System.out.print("Enter patron ID: ");
        String patronId = menuScanner.nextLine();

        ArrayList<BorrowingRecord> records = borrowingRecordController.getBorrowingRecordsByPatronId(patronId);

        if(records.isEmpty())
        {
            System.out.println("No borrowing records found for patron with ID " + patronId + ".");
        }
        else
        {
            System.out.println("Borrowing records of patron with ID " + patronId + ":");
            System.out.println("----------------------------------");

            for (BorrowingRecord record : records)
            {
                record.displayPublicDetails();
            }

            System.out.println("----------------------------------");

            System.out.print("Enter borrowing record ID to edit: ");
            String recordId = menuScanner.nextLine();
            BorrowingRecord record = borrowingRecordController.getBorrowingRecordById(recordId);

            if (record != null)
            {
                System.out.print("Enter new status (Active, Returned, Returned Overdue, Overdue): ");
                String status = menuScanner.nextLine();

                if (status != null
                        && (status.equals("Active")
                        || status.equals("Returned")
                        || status.equals("Returned Overdue")
                        || status.equals("Overdue")))
                {
                    record.setRecordStatus(status);
                    borrowingRecordController.updateBorrowingRecord(record);

                    System.out.println("Borrowing record status updated successfully.");
                }
                else
                {
                    System.out.println("Invalid status. Please try again.");
                }
            }
            else
            {
                System.out.println("Borrowing record with ID " + recordId + " not found.");
            }
        }

        System.out.println("Press enter to continue...");
        menuScanner.nextLine();
    }

    // Utilities

    private void displayBooks(ArrayList<Book> books)
    {
        if (books.isEmpty())
        {
            System.out.println("No books found.");
        }
        else
        {
            System.out.println(books.size() + " book(s) found:");
            System.out.println("----------------------------------");

            for (Book book : books)
            {
                book.displayDetails();
            }

            System.out.println("----------------------------------");
        }
    }

    private void displayKeywordedBooks(ArrayList<Book> books, String keyword)
    {
        if (books.isEmpty())
        {
            System.out.println("No books found with the keyword '" + keyword + "'.");
        }
        else
        {
            System.out.println(books.size() + " book(s) found with the keyword '" + keyword + "':");
            System.out.println("----------------------------------");

            for (Book book : books)
            {
                book.displayDetails();
            }

            System.out.println("----------------------------------");
        }
    }

    // Helpers

    private String generateBookId()
    {
        Book book = bookController.getLatestBook();
        int latestNumber = Integer.parseInt(book.getId().substring(3));

        return String.format("BID%04d", latestNumber + 1);
    }

    // Overrides

    /**
     * Displays the menu and handles user choices.
     */
    @Override
    public void showMenu()
    {
        while (isRunning)
        {
            System.out.print(MENU_CONTENT);

            int choice = askForChoice();
            switch (choice)
            {
                case LOGOUT:
                    logout();
                    break;
                case VIEW_BOOKS:
                    viewBooks();
                    break;
                case BROWSE_BOOKS:
                    browseBooks();
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case EDIT_BOOK:
                    editBook();
                    break;
                case REMOVE_BOOK:
                    removeBook();
                    break;
                case UPDATE_BORROWING_RECORD_STATUS:
                    updateBorrowingRecordStatus();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        }
    }

    /**
     * Prompts the user to input their choice and returns it as an integer.
     *
     * @return the user's choice
     */
    @Override
    public int askForChoice()
    {
        return Integer.parseInt(menuScanner.nextLine());
    }

}
