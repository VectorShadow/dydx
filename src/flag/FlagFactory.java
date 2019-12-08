package flag;

public class FlagFactory {
    private String name;
    private double x = -1;
    private int y = -1;

    private FlagFactory(String name){
        this.name = name;
    }
    public static FlagFactory setName(String name) {
        return new FlagFactory(name);
    }
    public FlagFactory setPower(double p) {
        x = p;
        return this;
    }
    public FlagFactory setPower(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public Flag manufacture(){
        if (x < 0) return new BasicFlag(name);
        if (y < 0) return new SingleFlag(name, x);
        return new DoubleFlag(name, (int)x, y);
    }
}
