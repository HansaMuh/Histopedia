package menus;

import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import models.*;
import singleton.Database;

/**
 * The LibrarianMenu class extends the Menu class.
 */
public class LibrarianMenu extends Menu
{
	private Database database;
	Scanner scan = new Scanner(System.in);

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
    private final String MENU_CONTENT = "Welcome to Histopedia, " + currentUser.getUsername() + "!\n" +
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
    }

    /**
     * Displays all books in the library.
     */
    private void viewBooks()
    {
        // TODO: Implement viewBooks method
    }

    /**
     * Browses books by title, genre, or author.
     */
    private void browseBooks()
    {
        // TODO: Implement browseBooks method
    }

    /**
     * Adds a new book to the library.
     */
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 8;
    
    private String generateRandomId() {
        String id = "";
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = (int) (Math.random() * CHARACTERS.length());
            id += CHARACTERS.charAt(index); // Menambahkan karakter ke string ID
        }
        return id;
    }
    
    private void addBook()
    {
    	String id = generateRandomId();

        String title;
        do {
            System.out.print("Enter book title (at least 5 characters): ");
            title = scan.nextLine();
            if (title.length() < 5) {
                System.out.println("Title must be at least 5 characters long.");
            }
        } while (title.length() < 5);

        String genre;
        do {
            System.out.print("Enter book genre (biography, military, revolution): ");
            genre = scan.nextLine().toLowerCase();
            if (!genre.equalsIgnoreCase("biography") && !genre.equalsIgnoreCase("military") && !genre.equalsIgnoreCase("revolution")) {
                System.out.println("Genre must be biography, military, or revolution.");
            }
        } while (!genre.equalsIgnoreCase("biography") && !genre.equalsIgnoreCase("military") && !genre.equalsIgnoreCase("revolution"));

        String description;
        do {
            System.out.print("Enter book description (at least 5 words): ");
            description = scan.nextLine();
            if (description.split("\\s+").length < 5) {
                System.out.println("Description must contain at least 5 words.");
            }
        } while (description.split("\\s+").length < 5);

        int publicationYear;
        int currentYear = Year.now().getValue();
        do {
            System.out.print("Enter publication year (must be " + currentYear + " or later): ");
            publicationYear = scan.nextInt();
            scan.nextLine();
            if (publicationYear < currentYear) {
                System.out.println("Publication year must be " + currentYear + " or later.");
            }
        } while (publicationYear < currentYear);
	    
        String author;
        do {
        	System.out.print("Enter book author (at least 5 characters): ");
            author = scan.nextLine();
            if(author.length()<5) {
            	System.out.println("Author must be at least 5 characters long.");
            }
        }while(author.length()<5);
	    
        String publisher;
        do {
        	System.out.print("Enter book publisher (at least 5 characters): ");
            publisher = scan.nextLine();
            if(publisher.length()<5) {
            	System.out.println("Publisher must be at least 5 characters long.");
            }
        }while(publisher.length()<5);
	    
        System.out.print("Enter book ISBN: ");
        String isbn = scan.nextLine();
	    
        String status = "Available";

        if(genre.equalsIgnoreCase("biography")) {
        	Book book = new BiographyBook(id, title, genre, description, author, publisher, publicationYear, isbn, status);
        	database.addBook(book);
        }
        else if(genre.equalsIgnoreCase("military")) {
        	Book book = new MilitaryHistoryBook(id, title, genre, description, author, publisher, publicationYear, isbn, status);
        	database.addBook(book);
        }
        else if(genre.equalsIgnoreCase("revolution")) {
        	Book book = new RevolutionBook(id, title, genre, description, author, publisher, publicationYear, isbn, status);
        	database.addBook(book);
        }
        System.out.println("Book with id " + id + " added successfully.");
        System.out.println("Press enter to continue...");scan.nextLine();
    }

    /**
     * Edits an existing book in the library.
     */
    private void editBook()
    {
        // TODO: Implement editBook method
    }

    /**
     * Removes a book from the library.
     */
    private void removeBook()
    {
    	
        System.out.print("Enter the ID of the book to delete: ");
        String id = scan.nextLine();
        Book book = database.getBookById(id);
        
        if(book != null) {
        	database.deleteBook(id);
        	System.out.println("Book with ID " + id + " has been deleted.");
        }else {
        	System.out.println("Book with ID " + id + " not found.");
        	System.out.println("Please enter to continue...");scan.nextLine();
        }
        System.out.println("Press enter to continue...");scan.nextLine();
        
    }

    /**
     * Updates the status of a borrowing record.
     */
    private void updateBorrowingRecordStatus()
    {
        System.out.print("Enter patron ID: ");
        String patronId = scan.nextLine();

        ArrayList<BorrowingRecord> records = Database.getInstance().getBorrowingRecordsByPatronId(patronId);

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
            String recordId = scan.nextLine();
            BorrowingRecord record = Database.getInstance().getBorrowingRecordById(recordId);

            if (record != null)
            {
                System.out.print("Enter new status (Active, Returned, Returned Overdue, Overdue): ");
                String status = scan.nextLine();

                if (status != null
                        && (status.equals("Active")
                        || status.equals("Returned")
                        || status.equals("Returned Overdue")
                        || status.equals("Overdue")))
                {
                    record.setRecordStatus(status);
                    Database.getInstance().updateBorrowingRecord(record);

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
    }

    // Utilities

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
        }

        System.out.println("Exiting the program...");
        System.exit(0);
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
