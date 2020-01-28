package gui;

import actor.Actor;

/**
 * Keeps track of where the player is looking in order to properly draw the screen.
 */
public class Camera {
    private static Actor focus = null;
    private static int row = -1;
    private static int col = -1;

    static int row(){
        return row < 0 ? focus.getMapCoordinate().ROW : row;
    }
    static int col() {
        return col < 0 ? focus.getMapCoordinate().COL : col;
    }
    public static void setCamera(int r, int c) {
        row = r;
        col = c;
    }
    public static void setFocus(Actor a) {
        focus = a;
        setCamera(-1, -1);
    }

}
