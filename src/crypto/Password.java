package crypto;

import error.ErrorLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {

    static final int MAX_CHAR = 255;

    private static final int MINIMUM_LENGTH = 8;
    private static final int MAXIMUM_LENGTH = 32;
    private static final int SALT_LENGTH = 8;

    private static char forceAlphaNumericalSymbolic(char candidate, int seed){
        char result = candidate;
        int iterations = 0;
        while (result < '!' || result == '/' || result == '\\' || result > '~'){
            int productStep = (result + iterations) * seed;
            int modStep = productStep % MAX_CHAR;
            seed += iterations++;
            result = (char)modStep;
        }
        return result;
    }

    public static String hash(String saltedPassword){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String sha256 = new String(md.digest(saltedPassword.getBytes()));
            return Cipher.toHexString(sha256);
        } catch (NoSuchAlgorithmException e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
            return null; //exception should never be thrown, and if it is we crash, but java requires this
        }
    }

    public static String generateRandomSalt() {
        char nextCandidateChar;
        String randomSalt = "";
        char nextAlphaSeed = 1;
        for (int i = 0; i < SALT_LENGTH; ++i){
            nextCandidateChar = (char)Cipher.SECURE_RANDOM.nextInt(MAX_CHAR);
            randomSalt += forceAlphaNumericalSymbolic(nextCandidateChar, nextAlphaSeed);
            nextAlphaSeed = nextCandidateChar;
        }
        return randomSalt;
    }

    public static String salt(String salt, String unsaltedPassword) {
        return salt + unsaltedPassword;
    }
}
