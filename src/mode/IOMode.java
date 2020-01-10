package mode;

import contract.input.InputDialog;
import contract.menu.MenuHandler;

public class IOMode {
    public static long uniqueSerialID = 0;

    private final int channelIndex;
    private final InputContext inputContext;
    private final InputDialog inputDialog;
    private final MenuHandler menuHandler;
    private long uid;

    public IOMode(int channel, InputContext context) {
        this(channel, context, null, null);
    }
    public IOMode(int channel, InputContext context, InputDialog dialog) {
        this(channel, context, dialog, null);
    }
    public IOMode(int channel, InputContext context, MenuHandler handler) {
        this(channel, context, null, handler);
    }
    public IOMode(int channel, InputContext context, InputDialog dialog, MenuHandler handler) {
        channelIndex = channel;
        inputContext = context;
        inputDialog = dialog;
        menuHandler = handler;
        uid = uniqueSerialID++;
    }

    public int getChannelIndex() {
        return channelIndex;
    }

    public InputContext getInputContext() {
        return inputContext;
    }

    public InputDialog getInputDialog() {
        return inputDialog;
    }

    public MenuHandler getMenuHandler() {
        return menuHandler;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IOMode && ((IOMode) o).uid == uid;
    }
}
