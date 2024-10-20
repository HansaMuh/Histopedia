package models;

public abstract class User
{

    // Constructor

    /**
     * Initializes a new instance of the User class.
     *
     * @param id The unique identifier of the user.
     * @param accessLevel The access level of the user.
     * @param username The username of the user.
     * @param emailAddress The email address of the user.
     * @param password The password of the user.
     */
    public User(String id, int accessLevel, String username, String emailAddress, String password)
    {
        this.id = id;
        this.accessLevel = accessLevel;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    // Properties

    private final String id;
    private final int accessLevel; // Access levels: 0 (Librarian), 1 (Patron)
    private String username;
    private String emailAddress;
    private String password;

    // Getters

    /**
     * Gets the unique identifier of the user.
     *
     * @return The unique identifier of the user.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Gets the access level of the user.
     *
     * @return The access level of the user.
     */
    public int getAccessLevel()
    {
        return this.accessLevel;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmailAddress()
    {
        return this.emailAddress;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword()
    {
        return this.password;
    }

    // Setters

    /**
     * Sets the username of the user.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Sets the email address of the user.
     *
     * @param emailAddress The email address of the user.
     */
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

}
