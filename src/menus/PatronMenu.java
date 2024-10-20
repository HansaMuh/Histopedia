package menus;

import models.*;

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
    private final String MENU_CONTENT = "Welcome to Histopedia, " + currentUser.getUsername() + "!\n" +
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
    }

    /**
     * Displays all books in the library.
     */
    private void viewBooks()
    {
        // TODO: Implement viewBooks method
    }

    /**
     * Displays all books currently borrowed by the user.
     */
    private void viewCurrentlyBorrowedBooks()
    {
        // TODO: Implement viewCurrentlyBorrowedBooks method
    }

    /**
     * Displays all borrowing records with status.
     */
    private void seeBorrowingRecords()
    {
        // TODO: Implement seeBorrowingRecords method
    }

    /**
     * Allows the user to browse books by title, genre, or author.
     */
    private void browseBooks()
    {
        // TODO: Implement browseBooks method
    }

    /**
     * Allows the user to borrow a book.
     */
    private void borrowBook()
    {
        // TODO: Implement borrowBook method
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