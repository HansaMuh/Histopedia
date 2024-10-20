package models;

public class Librarian extends User
{

    // Constructor

    /**
     * Initializes a new instance of the Librarian class.
     *
     * @param id The unique identifier of the librarian.
     * @param username The username of the librarian.
     * @param emailAddress The email address of the librarian.
     * @param password The password of the librarian.
     */
    public Librarian(String id, String username, String emailAddress, String password)
    {
        super(id, 0, username, emailAddress, password);
    }

}
