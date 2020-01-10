package mode;

import contract.menu.Menu;
import contract.menu.MenuHandler;

public class IOMode {
    public static long uniqueSerialID = 0;

    private final int channelIndex;
    private final InputContext inputContext;
    private final MenuHandler menuHandler;
    private long uid;

    public IOMode(int channel, InputContext context) {
        this(channel, context, null);
    }
    public IOMode(int channel, InputContext context, MenuHandler handler) {
        channelIndex = channel;
        inputContext = context;
        menuHandler = handler;
        uid = uniqueSerialID++;
    }

    public int getChannelIndex() {
        return channelIndex;
    }

    public InputContext getInputContext() {
        return inputContext;
    }

    public MenuHandler getMenuHandler() {
        return menuHandler;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IOMode && ((IOMode) o).uid == uid;
    }
}
