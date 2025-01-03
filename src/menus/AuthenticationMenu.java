package menus;

import factories.PatronFactory;
import models.BorrowingRecord;
import models.User;
import singleton.Database;

/**
 * The AuthenticationMenu class extends the Menu class and provides
 * functionality for user authentication, including login and registration.
 */
public class AuthenticationMenu extends Menu
{

    // Constructor

    /**
     * Constructs a new AuthenticationMenu object and displays the menu.
     */
    public AuthenticationMenu()
    {
        super();

        showMenu();
    }

    // Fields

    /**
     * The content of the authentication menu.
     */
    private final String MENU_CONTENT = "Welcome to Histopedia!\n" +
            "1. Login\n" +
            "2. Register\n" +
            "Enter your choice >> ";
    /**
     * Constant representing the login option.
     */
    private final int LOGIN = 1;
    /**
     * Constant representing the register option.
     */
    private final int REGISTER = 2;

    // Methods

    /**
     * Handles the login process by prompting the user for their email address and password.
     */
    private void login()
    {
        // TODO: Implement login process with Email and Password
        String email = "";
        while (email.length() < 5)
        {
            System.out.print("Enter your email address (at least 5 characters): ");
            email = menuScanner.nextLine();
        }

        String password = "";
        while (password.length() < 5)
        {
            System.out.print("Enter your password (at least 5 characters): ");
            password = menuScanner.nextLine();
        }

        User user = userController.getUserByLoginInfo(email, password);
        if (user != null)
        {
            System.out.println("Login successful!");
            System.out.println("Press enter to continue...");
            menuScanner.nextLine();

            isRunning = false;
            switch (user.getAccessLevel())
            {
                case 0:
                    new LibrarianMenu(user);
                    break;
                case 1:
                    new PatronMenu(user);
                    break;
            }
        }
        else
        {
            System.out.println("Login failed. Please try again.");
            System.out.println("Press enter to continue...");
            menuScanner.nextLine();
        }
    }

    /**
     * Handles the registration process by prompting the user for their username, email address, and password.
     */
    private void register()
    {
        // TODO: Implement registration process with Username, Email, and Password
        String username = "";
        while (username.length() < 3 || username.length() > 20)
        {
            System.out.print("Enter your username (3-20 characters): ");
            username = menuScanner.nextLine();
        }

        String email = "";
        while (email.length() < 5)
        {
            System.out.print("Enter your email address (at least 5 characters): ");
            email = menuScanner.nextLine();
        }

        String password = "";
        while (password.length() < 5)
        {
            System.out.print("Enter your password (at least 5 characters): ");
            password = menuScanner.nextLine();
        }

        User patronUser = new PatronFactory().createUser(
                generateUserId(),
                username,
                email,
                password
        );
        userController.addUser(patronUser);

        System.out.println("Registration successful!");
        System.out.println("Press enter to continue...");
        menuScanner.nextLine();
    }

    // Utilities

    private String generateUserId()
    {
        User user = userController.getLatestUser();
        int latestNumber = Integer.parseInt(user.getId().substring(3));

        return String.format("UID%04d", latestNumber + 1);
    }

    // Overrides

    /**
     * Displays the authentication menu and handles user choices.
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
                case LOGIN:
                    login();
                    break;
                case REGISTER:
                    register();
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