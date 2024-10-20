package menus;

import java.util.*;

/**
 * The Menu class is an abstract class that provides a template for creating
 * different types of menus. It includes basic properties and methods that
 * are common to all menus.
 */
public abstract class Menu
{

    // Constructor

    /**
     * Constructs a new Menu object. Initializes the Scanner for user input
     * and sets the running state to true.
     */
    public Menu()
    {
        menuScanner = new Scanner(System.in);
        isRunning = true;
    }

    // Properties

    /**
     * Scanner object for reading user input.
     */
    protected Scanner menuScanner;

    /**
     * Boolean flag to indicate if the menu is running.
     */
    protected boolean isRunning;

    // Menu Methods

    /**
     * Exits the program by setting the running state to false and terminating the application.
     */
    protected void exit()
    {
        isRunning = false;

        System.out.println("Exiting the program...");
        System.exit(0);
    }

    // Abstract Methods

    /**
     * Abstract method to display the menu. Must be implemented by subclasses.
     */
    public abstract void showMenu();

    /**
     * Abstract method to ask the user for a choice. Must be implemented by subclasses.
     *
     * @return the user's choice as an integer
     */
    public abstract int askForChoice();

}