package flag;

public abstract class AbstractFlag {
    String name;

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("this: " + this + " o: " + o);
        System.out.println(this.name);
        System.out.println(((AbstractFlag)o).name);
        return o instanceof AbstractFlag && name.equals(((AbstractFlag) o).name);
    }
}
