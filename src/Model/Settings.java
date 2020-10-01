package Model;

import java.util.Hashtable;

public class Settings {

    private int backgroundColour[];
    private boolean dateDisplay;
    private boolean timeFormat;
    private Hashtable<String, String> userDb = new Hashtable<String, String>();

    //constructor
    public Settings() {}

    //adds new username and password to userDb
    public void registerUser(String username, String password)
    {
        userDb.put(username, password);
    }

    /*deletes an entry from userDb and returns true if it exists, else returns false
    /password validation is embedded in the method for additional security
     */
    public boolean deleteUser(String username, String inpPassword)
    {
        //userDb.get throws exception if username doesn't exist
        try
        {
            String password = userDb.get(username);
            //if inpPassword is correct, delete account and return true
            if(password == inpPassword)
            {
                userDb.remove(inpPassword);
                return true;
            }
            //if inpPassword is incorrect return false
            else
            {
                return false;
            }
        }
        //if username doesn't exist return false
        catch(Exception ex)
        {
            return false;
        }
    }

    //checks a username exists in the db
    public boolean containsUsername(String username)
    {
        return userDb.containsKey(username);
    }

    //----ACCESSOR METHODS----//
    public int[] getBackgroundColour()
    {
        return backgroundColour;
    }

    public boolean getDateDisplay()
    {
        return dateDisplay;
    }

    public boolean getTimeFormat()
    {
        return timeFormat;
    }

    //gets a password for the given username
    public String getPassword(String username) throws NullPointerException
    {
        return userDb.get(username);
    }

    //----MUTATOR METHODS----//
    public void setBackgroundColour(int r, int g, int b)
    {
        this.backgroundColour = new int[3];
        this.backgroundColour[0] = r;
        this.backgroundColour[1] = g;
        this.backgroundColour[2] = b;
    }

    public void setDateDisplay(boolean date)
    {
        this.dateDisplay = date;
    }

    public void setTimeFormat(boolean time)
    {
        this.timeFormat = time;
    }

    /*if inpPassword matches the password stored for inpUsername, update password to newPassword
    returns true if successful change, false if unsuccessful
     */
    public boolean setPassword(String inpUsername, String inpPassword, String newPassword)
    {
        try
        {
            String heldPassword = userDb.get(inpUsername);
            //if user enters correct old password
            if(heldPassword == inpPassword)
            {
                userDb.remove(inpUsername);
                userDb.put(inpPassword, newPassword);
                return true;
            }
            //if incorrect old password entered
            else
            {
                return false;
            }
        }
        //if exception is thrown do nothing and return
        catch(Exception ex)
        {
            return false;
        }
    }
}