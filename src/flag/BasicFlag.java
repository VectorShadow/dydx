package flag;

public class BasicFlag extends AbstractFlag implements Flag {

    BasicFlag(String name) {
        this.name = name;
    }

    @Override
    public double getPower() {
        return 0;
    }
}
