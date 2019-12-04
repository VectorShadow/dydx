package crypto;

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
        final char[] HASH_CONSTANT =
                {
                        139, 47, 83, 61, 2, 93, 241, 101,
                        44, 22, 11, 200, 96, 44, 83, 2,
                        191, 38, 38, 76, 119, 6, 14, 83,
                        70, 2, 87, 175, 40, 88, 177, 209,
                        18, 131, 94, 88, 7, 59, 109, 8
                };
        int length = saltedPassword.length();
        String hashedPassword = "";
        char seedIndex = (char)((HASH_CONSTANT[0] * saltedPassword.charAt(MINIMUM_LENGTH + SALT_LENGTH - 1)) % length);
        char seedChar = saltedPassword.charAt(seedIndex);
        char stepVector = (char)((length * seedChar) % MAX_CHAR);
        char baseChar = saltedPassword.charAt(0);
        for (int i = 0; i < MAXIMUM_LENGTH + SALT_LENGTH; ++i){
            int sumStep = baseChar + HASH_CONSTANT[i];
            int productStep = sumStep * stepVector;
            int modStep = productStep % MAX_CHAR;
            char candidate = (char)modStep;
            char result = forceAlphaNumericalSymbolic(candidate, baseChar);
            baseChar = candidate;
            stepVector =
                    (char)((result + saltedPassword.charAt(i % SALT_LENGTH)) % MAX_CHAR);
            hashedPassword += result;
        }
        return hashedPassword;
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
