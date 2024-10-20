package menus;

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
    }

    /**
     * Handles the registration process by prompting the user for their username, email address, and password.
     */
    private void register()
    {
        // TODO: Implement registration process
    }

    // Utilities

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