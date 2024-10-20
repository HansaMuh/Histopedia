package factories;

import models.*;

/**
 * The UserFactory abstract class provides a method for creating User objects.
 */
public abstract class UserFactory
{

    // Methods

    /**
     * Creates a new User instance.
     *
     * @param id The unique identifier for the user.
     * @param username The username of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @return A new User instance.
     */
    public abstract User createUser(String id, String username, String email, String password);

}