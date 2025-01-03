package factories;

import models.*;

/**
 * The LibrarianFactory class extends the UserFactory class to create
 * instances of the Librarian class.
 */
public class LibrarianFactory extends UserFactory
{

    // Methods

    /**
     * Creates a new Librarian instance with default values.
     *
     * @return A new Librarian instance.
     */
    @Override
    public User createUser()
    {
        return new Librarian(
                "UID000",
                "Default",
                "default@email.com",
                "default"
        );
    }

    /**
     * Creates a new Librarian instance.
     *
     * @param id The unique identifier for the librarian.
     * @param username The username of the librarian.
     * @param email The email address of the librarian.
     * @param password The password of the librarian.
     * @return A new Librarian instance.
     */
    @Override
    public User createUser(
            String id,
            String username,
            String email,
            String password)
    {
        return new Librarian(
                id,
                username,
                email,
                password
        );
    }

}