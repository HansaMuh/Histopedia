package models;

public class Patron extends User
{

    // Constructor

    /**
     * Initializes a new instance of the Patron class.
     *
     * @param id The unique identifier of the patron.
     * @param username The username of the patron.
     * @param emailAddress The email address of the patron.
     * @param password The password of the patron.
     */
    public Patron(String id, String username, String emailAddress, String password)
    {
        super(id, 1, username, emailAddress, password);
    }

}
