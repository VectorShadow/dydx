package flag;

public class SingleFlag extends AbstractFlag implements Flag {

    double power;

    SingleFlag(String name, double power) {
        this.name = name;
        this.power = power;
    }

    @Override
    public double getPower() {
        return power;
    }
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof SingleFlag && power == ((SingleFlag) o).power;
    }
}
