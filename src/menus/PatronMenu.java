package menus;

import factories.BorrowingRecordFactory;
import models.*;
import singleton.Database;

import java.util.*;

/**
 * The PatronMenu class extends the Menu class.
 */
public class PatronMenu extends Menu
{

    // Constructor

    /**
     * Constructs a new PatronMenu object and displays the menu.
     */
    public PatronMenu(User user)
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
    private final String MENU_CONTENT = "Welcome to Histopedia, " + currentUser.getUsername() + "! You're currently logged in as a Patron.\n" +
            "1. View books\n" +
            "2. View currently borrowed books\n" +
            "3. See borrowing records with status\n" +
            "4. Browse books by title / genre / author\n" +
            "5. Borrow book\n" +
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
     * Constant representing the menu option to view currently borrowed books.
     */
    private final int VIEW_CURRENTLY_BORROWED_BOOKS = 2;

    /**
     * Constant representing the menu option to see borrowing records with status.
     */
    private final int SEE_BORROWING_RECORDS = 3;

    /**
     * Constant representing the menu option to browse books by title, genre, or author.
     */
    private final int BROWSE_BOOKS = 4;

    /**
     * Constant representing the menu option to borrow a book.
     */
    private final int BORROW_BOOK = 5;

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
     * Displays all books currently borrowed by the user.
     */
    private void viewCurrentlyBorrowedBooks()
    {
        ArrayList<Book> books = bookController.getBorrowedBooksByPatronId(currentUser.getId());

        if (books.isEmpty())
        {
            System.out.println("You currently have no books being borrowed yet.");
        }
        else
        {
            System.out.println(books.size() + " books currently borrowed by " + currentUser.getUsername() + ":");
            System.out.println("----------------------------------");

            for (Book book : books)
            {
                book.displayDetails();
            }

            System.out.println("----------------------------------");
        }

        System.out.println();
        System.out.println("Press enter to continue...");
        menuScanner.nextLine();
    }

    /**
     * Displays all borrowing records with status.
     */
    private void seeBorrowingRecords()
    {
        ArrayList<BorrowingRecord> records = borrowingRecordController.getBorrowingRecordsByPatronId(currentUser.getId());

        if (records.isEmpty())
        {
            System.out.println("You have no borrowing records yet.");
        }
        else
        {
            System.out.println("Borrowing records of " + currentUser.getUsername() + ":");
            System.out.println("----------------------------------");

            for (BorrowingRecord record : records)
            {
                record.displayPublicDetails();
            }

            System.out.println("----------------------------------");
        }

        System.out.println();
        System.out.println("Press enter to continue...");
        menuScanner.nextLine();
    }

    /**
     * Allows the user to browse books by title, genre, or author.
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
     * Allows the user to borrow a book.
     */
    private void borrowBook()
    {
        // TODO: Implement borrowBook method
        String bookId = "";
        while (bookId.isEmpty())
        {
            System.out.print("Enter the ID of the book you want to borrow: ");
            bookId = menuScanner.nextLine();
        }

        Book book = bookController.getBookById(bookId);
        if (book == null)
        {
            System.out.println("Book not found with ID '" + bookId + "'.");
        }
        else
        {
            if (book.getStatus().equals("Available"))
            {
                Date currentDate = new Date();

                BorrowingRecordFactory borrowingRecordFactory = new BorrowingRecordFactory();
                BorrowingRecord record = borrowingRecordFactory.createBorrowingRecord(
                        generateBorrowingRecordId(),
                        book.getId(),
                        currentUser.getId(),
                        null,
                        "Active",
                        currentDate,
                        new Date(currentDate.getTime() + 604800000),
                        -1,
                        null
                );

                borrowingRecordController.addBorrowingRecord(record);

                System.out.println("Book borrowed successfully.");
                System.out.println("Press enter to continue...");
                menuScanner.nextLine();
            }
            else
            {
                System.out.println("Book is not available for borrowing.");
            }
        }

    }

    // Utilities

    private void displayBooks(ArrayList<Book> books)
    {
        if (books.isEmpty())
        {
            System.out.println("There are no books in the library yet.");
        }
        else
        {
            System.out.println(books.size() + " books found in the library:");
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

    private String generateBorrowingRecordId()
    {
        BorrowingRecord borrowingRecord = borrowingRecordController.getLatestBorrowingRecord();
        int latestNumber = Integer.parseInt(borrowingRecord.getId().substring(3));

        return String.format("RID%04d", latestNumber + 1);
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
                case VIEW_CURRENTLY_BORROWED_BOOKS:
                    viewCurrentlyBorrowedBooks();
                    break;
                case SEE_BORROWING_RECORDS:
                    seeBorrowingRecords();
                    break;
                case BROWSE_BOOKS:
                    browseBooks();
                    break;
                case BORROW_BOOK:
                    borrowBook();
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