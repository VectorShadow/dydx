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

    /**
     * Local version - Cipher.SESSION_KEY is all that's required.
     */
    public String decryptPassword(){
        return Cipher.decrypt(encryptedPassword);
    }

    /**
     * Remote version - we need a key received from a remote source.
     */
    public String decryptPassword(String secretKey) {
        return Cipher.decrypt(encryptedPassword, secretKey);
    }
}
