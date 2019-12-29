package flag;

import java.util.Random;

public class DoubleFlag extends AbstractFlag implements Flag {

    private static Random roll = new Random();

    int x;
    int y;

    DoubleFlag(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
    }
    public double average() {
        return x * y / 2.0;
    }

    @Override
    public double getPower() {
        int sum = 0;
        for (int i = 0; i < x; ++i) sum += (roll.nextInt(y) + 1);
        return sum;
    }
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof DoubleFlag && x == ((DoubleFlag) o).x && y == ((DoubleFlag) o).y;
    }
}
