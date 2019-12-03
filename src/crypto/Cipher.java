package crypto;

import java.security.SecureRandom;

public class Cipher {

    static final SecureRandom SECURE_RANDOM = new SecureRandom();


    private static final int SHIFT = 7;
    private static final int KEY_SIZE = 128;

    private static final char UPPER_MASK = 0xf0; //mask for converting hex chars to normal chars
    private static final char LOWER_MASK = 0x0f; //mask for converting hex chars to normal chars
    private static final int BYTE_MASK = 0x0000_007f;

    private static String sessionKey;

    /**
     * Client side - generate a key to encrypt sensitive data (passwords etc.)
     */
    public static void generateSessionKey(){
        sessionKey = "";
        while (sessionKey.length() < KEY_SIZE){
            sessionKey += (byte) SECURE_RANDOM.nextInt(Password.MAX_CHAR);
        }
    }

    /**
     * Client side - get the current session key for transmission to server.
     * We use this to build a BigInteger, so it must be in radix form.
     */
    public static String getSessionKey(){
        return toHexString(sessionKey);
    }
    /**
     * Server side - set the session key received from the client.
     */
    public static void setSessionKey(String key) {
        sessionKey = fromHexString(key);
    }

    private static byte shift(byte b, int mag, boolean left) {
        if (mag < 0 || mag > SHIFT) throw new IllegalArgumentException("Mag must be in range [0-7].");
        if (mag == 0 || mag == SHIFT) return b;
        byte shiftMask = 0b0000_0000;
        int factor = 1;
        int oppositeMag = SHIFT - mag;
        for (int i = 0; i < (left ? oppositeMag : mag); ++i) {
            shiftMask += factor;
            factor *= 2;
        }
        byte oppositeMask = (byte)(~(int)shiftMask & BYTE_MASK);
        byte leftShiftPart = (byte)((int)b & (int)shiftMask);
        byte rightShiftPart = (byte)((int)b & (int)oppositeMask);
        return (byte)((leftShiftPart << (left ? mag : oppositeMag)) | (rightShiftPart >>> (left ? oppositeMag : mag)));
    }

    /**
     * Encryption step - rotate bytes leftwards.
     */
    private static String bytewiseShiftLeft(String input) {
        byte[] inBytes = input.getBytes();
        byte[] outBytes = new byte[inBytes.length];
        int shiftDegree;
        for (int i = 0; i < inBytes.length; ++i) {
            shiftDegree = (i % SHIFT);
            outBytes[i] = shift(inBytes[i], shiftDegree, true);
        }
        return new String(outBytes);
    }

    /**
     * Decryption step - rotate bytes rightwards (reverse bytewiseShiftLeft).
     */
    private static String bytewiseShiftRight(String input) {
        byte[] inBytes = input.getBytes();
        byte[] outBytes = new byte[inBytes.length];
        int shiftDegree;
        for (int i = 0; i < inBytes.length; ++i) {
            shiftDegree = (i % SHIFT);
            outBytes[i] = shift(inBytes[i], shiftDegree, false);
        }
        return new String(outBytes);
    }

    /**
     * Encryption/Decryption step - xor the source text with the sessionKey.
     * This encrypts plain text or decrypts cipher text.
     */
    private static String xorApplyKey(String sourceText) {
        byte[] sourceBytes = sourceText.getBytes();
        byte[] keyBytes = sessionKey.getBytes();
        byte[] targetBytes = new byte[sourceBytes.length];
        for (int i = 0; i < sourceBytes.length; ++i)
            targetBytes[i] = (byte) (sourceBytes[i] ^ keyBytes[i % keyBytes.length]);
        return new String(targetBytes);
    }

    /**
     * Convert an integer value to an alphanumeric character representing that value in hexadecimal.
     */
    private static char hex(int intValue) {
        switch (intValue){
            case 0: return '0';
            case 1: return '1';
            case 2: return '2';
            case 3: return '3';
            case 4: return '4';
            case 5: return '5';
            case 6: return '6';
            case 7: return '7';
            case 8: return '8';
            case 9: return '9';
            case 10: return 'a';
            case 11: return 'b';
            case 12: return 'c';
            case 13: return 'd';
            case 14: return 'e';
            case 15: return 'f';
            default: throw new IllegalArgumentException("Invalid sourceChar " + intValue);
        }
    }

    /**
     * Convert an alphanumeric character representing a hexadecimal value to an int corresponding to that value.
     */
    public static int deHex(char hexChar) {
        switch (hexChar){
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            case 'a': return 10;
            case 'b': return 11;
            case 'c': return 12;
            case 'd': return 13;
            case 'e': return 14;
            case 'f': return 15;
            default: throw new IllegalArgumentException("Invalid sourceChar " + hexChar + " as Int: " + (int)hexChar);
        }
    }

    /**
     * Encryption step -
     * Converts a string into another string representing the byte values of the first string as hexadecimal characters.
     */
    private static String toHexString(String string) {
        String hexString = "";
        for(char c : string.toCharArray()){
            hexString += hex(((c & UPPER_MASK) >> 4));
            hexString += hex((c & LOWER_MASK));
        }
        return hexString;
    }

    /**
     * Decryption step -
     * Reverse to hexString by building a new string from the bytes represented by hexadecimal characters.
     */
    private static String fromHexString(String hexString) {
        byte[] out = new byte[hexString.length() / 2];
        for (int i = 0; i < out.length; i++){
            char upperHex = hexString.charAt(2*i);
            char upper = (char)((deHex(upperHex) << 4) & UPPER_MASK);
            char lowerHex = hexString.charAt(2*i + 1);
            char lower = (char)(deHex(lowerHex) & LOWER_MASK);
            out[i] = (byte)(upper | lower);
        }
        return new String(out);
    }

    /**
     * Encrypt a string for secure transmission.
     */
    public static String encrypt(String plainText){
        return bytewiseShiftLeft(xorApplyKey(toHexString(plainText)));
    }

    /**
     * Decrypt a cipher string.
     */
    public static String decrypt(String cipherText) {
        return fromHexString(xorApplyKey(bytewiseShiftRight(cipherText)));
    }

    public static boolean testAllCrypto() {
        generateSessionKey();
        return testShift() &&
                testRightShiftInvertsLeftShift() &&
                testBytewiseXor() &&
                testEncryptDecrypt();
    }
    private static boolean testShift(){
        byte b0 = 0b0000_0001;
        byte b1 = 0b0011_1100;
        byte b2 = 0b0101_0010;
        byte s01L = shift(b0, 1, true);
        byte s01R = shift(b0, 1, false);
        byte s03L = shift(b0, 3, true);
        byte s03R = shift(b0, 3, false);
        byte s12L = shift(b1, 2, true);
        byte s14R = shift(b1, 4, false);
        byte s20L = shift(b2, 0, true);
        byte s26R = shift(b2, 6, false);
        byte s26L = shift(b2, 6, true);
        byte s27R = shift(b2, 7, false);
        System.out.println(
                "\nTest 0: Shift 0000_0001 left one - expected: 2; actual: " + s01L +
                        "\nTest 1: Shift 0000_0001 right one - expected: 64; actual: " + s01R +
                        "\nTest 2: Shift 0000_0001 left three - expected: 8; actual: " + s03L +
                        "\nTest 3: Shift 0000_0001 right three - expected: 16; actual: " + s03R +
                        "\nTest 4: Shift 0011_1100 left two - expected: 113; actual: " + s12L +
                        "\nTest 5: Shift 0011_1100 right four - expected: 99; actual: " + s14R +
                        "\nTest 6: Shift 0101_0010 left zero - expected: 82; actual: " + s20L +
                        "\nTest 7: Shift 0101_0010 right six - expected: 00100101(37); actual: " + s26R +
                        "\nTest 8: Shift 0101_0010 left six - expected: 0010_1001(41); actual: " + s26L +
                        "\nTest 9: Shift 0101_0010 right seven - expected: 82; actual: " + s27R
        );
        return s01L == 2 && s01R == 64 && s03L == 8 && s03R == 16 && s12L == 113
                && s14R == 99 && s20L == 82 && s26R == 37 && s26L == 41 && s27R == 82;
    }
    private static boolean testRightShiftInvertsLeftShift() {
        String test0 = "test0000";
        boolean rightInvertsLeft0 = bytewiseShiftRight(bytewiseShiftLeft(test0)).equals(test0);
        boolean leftInvertsRight0 = bytewiseShiftLeft(bytewiseShiftRight(test0)).equals(test0);
        String test1 = "37ohsjk&&w kdllh&6%Y%VKIiiDNJ#JJH^^UJJDdjliliw?? .kdu012";
        boolean rightInvertsLeft1 = bytewiseShiftRight(bytewiseShiftLeft(test1)).equals(test1);
        boolean leftInvertsRight1 = bytewiseShiftLeft(bytewiseShiftRight(test1)).equals(test1);
        System.out.println("Test0rightleft " + (rightInvertsLeft0 ? "passed. " : "failed: " + bytewiseShiftRight(bytewiseShiftLeft(test0))));
        System.out.println("Test0leftright " + (leftInvertsRight0 ? "passed. " : "failed: " + bytewiseShiftLeft(bytewiseShiftRight(test0))));
        System.out.println("Test1rightleft " + (rightInvertsLeft1 ? "passed. " : "failed: " + bytewiseShiftRight(bytewiseShiftLeft(test1))));
        System.out.println("Test1leftright " + (leftInvertsRight1 ? "passed. " : "failed: " + bytewiseShiftLeft(bytewiseShiftRight(test1))));
        return rightInvertsLeft0 && leftInvertsRight0 && rightInvertsLeft1 && leftInvertsRight1;
    }
    private static boolean testBytewiseXor() {
        String test0 = "test0000";
        String xorResult = xorApplyKey(test0);
        String reverse = xorApplyKey(xorResult);
        boolean pass = test0.equals(reverse);
        System.out.println("TestBytewiseXor " + (pass ? "passed. " : "failed: " + test0 + " != " + reverse));
        return pass;
    }
    private static boolean testEncryptDecrypt() {
        String test0 = "Test message 0.";
        String test0partial = encrypt(test0);
        boolean pass0 = test0.equals(decrypt(test0partial));
        String test1 = "fjkeho3kj laf./fs&#Ijr8LKggft%";
        String test1partial = encrypt(test1);
        boolean pass1 = test1.equals(decrypt(test1partial));
        System.out.println("Test0 " + (pass0 ? "passed. " : "failed. "));
        System.out.println("Test1 " + (pass1 ? "passed. " : "failed. "));
        return pass0 && pass1;
    }
}
