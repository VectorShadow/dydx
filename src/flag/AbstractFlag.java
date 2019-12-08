package flag;

public abstract class AbstractFlag {
    String name;

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AbstractFlag && name.equals(((AbstractFlag) o).name);
    }
}
