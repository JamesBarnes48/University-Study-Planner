package Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* creating this class to make objects in the rest of the code. As this function is needed in many classes.*/
public class Hasher {
    private String hashedValue;
    public void setHashedValue(String enteredPWord) {
        // only stores a hashed value of the password for security. I will use the MD5 algo to produce a relatively short
        // hash value. Susceptible to brute force attacks and such
        // MD5 is not collision resistant which means that different passwords can eventually result in the same hash.
        String generatedPassword = null;
        // this function contains
        // md5 code from https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
        // I did not write the hashing algorithm myself. I implemented it into the project
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(enteredPWord.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        this.hashedValue = generatedPassword;
    }

    public void HasherClass() {
    }

    public String getHashedValue() {
        return hashedValue;
    }
}
