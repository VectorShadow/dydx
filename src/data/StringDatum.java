package data;

public class StringDatum extends AbstractDatum {
    private final String value;

    public StringDatum(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}
