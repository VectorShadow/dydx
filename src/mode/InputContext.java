package mode;

public class InputContext {
    public static long uniqueSerialID = 0;

    private long uid;

    public InputContext() {
        uid = uniqueSerialID++;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof InputContext && ((InputContext) o).uid == uid;
    }
}
