package gui;

/**
 * Keeps track of where the player is looking in order to properly draw the screen.
 */
public class Camera {
    private static int row = -1;
    private static int col = -1;

    static int row(){
        return row; //todo - if row < 0 find player row
    }
    static int col() {
        return col; //todo - if col < 0 find player col
    }
    public static void setCamera(int r, int c) {
        row = r;
        col = c;
    }
    public static void trackPlayer() {
        setCamera(-1, -1);
    }

}
