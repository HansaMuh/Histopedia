package factories;

import models.*;

/**
 * The PatronFactory class extends the UserFactory class to create
 * instances of the Patron class.
 */
public class PatronFactory extends UserFactory
{

    // Methods

    /**
     * Creates a new Patron instance with default values.
     *
     * @return A new Patron instance.
     */
    @Override
    public User createUser()
    {
        return new Patron(
                "UID000",
                "Default",
                "default@email.com",
                "default"
        );
    }

    /**
     * Creates a new Patron instance.
     *
     * @param id The unique identifier for the patron.
     * @param username The username of the patron.
     * @param email The email address of the patron.
     * @param password The password of the patron.
     * @return A new Patron instance.
     */
    @Override
    public User createUser(
            String id,
            String username,
            String email,
            String password)
    {
        return new Patron(
                id,
                username,
                email,
                password
        );
    }

}