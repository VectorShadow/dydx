package data;

import crypto.Cipher;

/**
 * Provides secure transmission of user data.
 * Password is encrypted client-side at creation and decrypted server-side for usage.
 */
public class UserDatum extends AbstractDatum {
    private String username;
    private String encryptedPassword;

    public UserDatum(String username, String plaintextPassword) {
        this.username = username;
        encryptedPassword = Cipher.encrypt(plaintextPassword);
    }

    public String getUsername() {
        return username;
    }

    public String decryptPassword() {
        return Cipher.decrypt(encryptedPassword);
    }
}
