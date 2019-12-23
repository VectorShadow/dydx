package data;

public class InstructionCode {
    public static final byte ERROR = -1;
    public static final byte PROTOCOL_BIG_INTEGER = 0;
    public static final byte PROTOCOL_QUERY_ACCOUNT = 1;
    public static final byte PROTOCOL_VERIFY_ACCOUNT = 2;
    public static final byte PROTOCOL_CREATE_ACCOUNT = 3;
    public static final byte DYDX_CODES = 3; //todo - keep updated!!

    /**
     * Safe method for defining implementation specific instruction codes.
     * Intended to prevent conflict with pre-defined instruction codes.
     *
     * Contract:
     * If Input is < 0, return ERROR.
     * If Input is < DYDX_CODES(the value associated with the number of pre-defined codes), return Input + DYDX_CODES.
     * Otherwise, simply return the Input.
     *
     * We will not define so many pre-defined codes that the second case could overflow and become negative.
     *
     * Usage:
     * public static final byte FOO = InstructionCode.define(fooVal);
     */
    public static byte define(byte value) {
        return value <= 0 ? ERROR : value <= DYDX_CODES ? ((byte)(value + DYDX_CODES)) : value;
    }
}
