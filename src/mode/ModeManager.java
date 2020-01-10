package mode;

import contract.Gui;

import java.util.Stack;

public class ModeManager {
    private static Stack<IOMode> modeStack = new Stack<>();
    private static Gui gui;

    public static void toMode(IOMode mode) {
        gui.changeChannel(mode.getChannelIndex());
        if (mode.getMenuHandler() != null) mode.getMenuHandler().printMenu(gui);
        if (mode.getInputDialog() != null) gui.printDialog(gui.rowAtPercent(.35), mode.getInputDialog());
    }
    public static void resetStack(IOMode mode) {
        modeStack = new Stack<>();
        pushMode(mode);
    }
    public static void pushMode(IOMode mode) {
        modeStack.push(mode);
        toMode(mode);
    }
    public static void popMode() {
        modeStack.pop();
        toMode(peekMode());
    }
    public static IOMode peekMode() {
        return modeStack.peek();
    }
    public static void setGui(Gui g) {
        gui = g;
    }
}
