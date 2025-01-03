package controllers;

import factories.*;
import models.Book;
import models.User;
import singleton.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController
{

    // Constructor

    public UserController()
    {
        database = Database.getInstance();
    }

    // Properties

    private final Database database;

    // Accessor Methods: User

    /**
     * Retrieves all users from the database.
     *
     * @return an ArrayList of User objects
     */
    public ArrayList<User> getUsersByAll()
    {
        return queryUsers("SELECT * FROM users");
    }

    /**
     * Retrieves a user from the database by their ID.
     *
     * @param id the ID of the user
     * @return the User object, or null if not found
     */
    public User getUserById(String id)
    {
        ArrayList<User> users = queryUsers("SELECT * FROM users WHERE id = ?", id);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Retrieves a user from the database by their login information.
     *
     * @param emailAddress the email address of the user
     * @param password the password of the user
     * @return the User object, or null if not found
     */
    public User getUserByLoginInfo(String emailAddress, String password)
    {
        ArrayList<User> users = queryUsers("SELECT * FROM users WHERE email_address = ? AND password = ?", emailAddress, password);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Retrieves the latest user added to the database.
     *
     * @return the User object, or default value if not found
     */
    public User getLatestUser()
    {
        PatronFactory patronFactory = new PatronFactory();
        ArrayList<User> users = queryUsers("SELECT * FROM users ORDER BY id DESC LIMIT 1");
        return users.isEmpty() ? patronFactory.createUser() : users.get(0);
    }

    /**
     * Adds a new user to the database.
     *
     * @param user the User object to add
     */
    public boolean addUser(User user)
    {
        int rowsAffected = updateUsers("INSERT INTO users " +
                        "(id, access_level, username, email_address, password) " +
                        "VALUES (?, ?, ?, ?, ?)",
                user.getId(),
                user.getAccessLevel(),
                user.getUsername(),
                user.getEmailAddress(),
                user.getPassword()
        );
        return rowsAffected > 0;
    }

    // Utilities

    private ArrayList<User> queryUsers(String query, Object... values)
    {
        ArrayList<User> users = new ArrayList<>();

        try (PreparedStatement statement = database.prepareStatement(query, values))
        {
            ResultSet results = statement.executeQuery();

            users.addAll(convertUsersByResultSet(results));
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return users;
    }

    private int updateUsers(String query, Object... values)
    {
        int rowsAffected = 0;

        try (PreparedStatement statement = database.prepareStatement(query, values))
        {
            rowsAffected = statement.executeUpdate();
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return rowsAffected;
    }

    private ArrayList<User> convertUsersByResultSet(ResultSet resultSet)
    {
        UserFactory userFactory = null;
        ArrayList<User> users = new ArrayList<>();

        try
        {
            while (resultSet.next())
            {
                switch (resultSet.getInt("access_level"))
                {
                    case 0:
                        userFactory = new LibrarianFactory();
                        break;
                    case 1:
                        userFactory = new PatronFactory();
                        break;
                }

                User user = userFactory.createUser(
                        resultSet.getString("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email_address"),
                        resultSet.getString("password")
                );

                users.add(user);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Query error detected.\nDetails:\n" + ex.getMessage());
        }

        return users;
    }

}
