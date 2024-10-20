package menus;

import models.*;

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
    private void addBook()
    {
        // TODO: Implement addBook method
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
        // TODO: Implement removeBook method
    }

    /**
     * Updates the status of a borrowing record.
     */
    private void updateBorrowingRecordStatus()
    {
        // TODO: Implement updateBorrowingRecordStatus method
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