package server;

import static server.FileManager.SEPARATOR;

public abstract class StringSaveable {

    protected String skipSeparator(String saveFileLine) {
        return saveFileLine.substring(saveFileLine.indexOf(SEPARATOR) + 1);
    }
    protected String nextSubstring(String saveFileLine) {
        return saveFileLine.substring(0, saveFileLine.indexOf(SEPARATOR));
    }
    protected int integer(String substring) {
        return Integer.parseInt(substring);
    }
}
