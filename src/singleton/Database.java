package singleton;

import java.sql.*;

/**
 * The Database class implements the Singleton design pattern to manage a single
 * instance of a database connection. It provides methods to get the instance
 * and the connection to the database.
 */
public class Database
{

    // Constructor

    /**
     * Private constructor to initialize the database connection.
     */
    private Database()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        }
        catch (Exception ex)
        {
            System.out.println("Connection error detected.\nDetails:\n" + ex.getMessage());
        }
    }

    // Fields

    /**
     * The single instance of the Database class.
     */
    private static Database instance;
    /**
     * The username for the database connection.
     */
    private final String USERNAME = "root";
    /**
     * The password for the database connection.
     */
    private final String PASSWORD = "";
    /**
     * The label for the database connection.
     */
    private final String DATABASE = "histopedia";
    /**
     * The host for the database connection.
     */
    private final String HOST = "localhost:3306";
    /**
     * The URL for the database connection.
     */
    private final String CONNECTION_STRING = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    // Properties

    /**
     * The connection object for the database.
     */
    private Connection connection;

    // Field Methods

    /**
     * Returns the single instance of the Database class, creating it if it does not
     * already exist.
     *
     * @return the single instance of the Database class
     */
    public static Database getInstance()
    {
        if (instance == null)
        {
            synchronized (Database.class)
            {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }

        return instance;
    }

    // Methods

    public PreparedStatement prepareStatement(String query, Object... values)
    {
        PreparedStatement statement = null;

        try
        {
            statement = connection.prepareStatement(query);

            int index = 1;
            for (Object val : values) {
                statement.setObject(index++, val);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return statement;
    }

}