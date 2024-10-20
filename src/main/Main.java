package main;

import menus.*;
import singleton.*;

public class Main
{

    // Program

    public static void main(String[] args)
    {
        Database.getInstance();
        new AuthenticationMenu();
    }

}
