package actor;

import engine.time.Time;
import graph.Coordinate;
import gui.draw.Drawable;
import resources.continuum.Pair;
import resources.glyph.ProtoGlyph;
import resources.glyph.ProtoGlyphBuilder;
import server.StringSaveable;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import static server.FileManager.SEPARATOR_STRING;

/**
 * An Actor is any object capable of changing the WorldState.
 * It maintains a queue of ActionEvents, which the Game Engine will access to initiate changes in due order.
 * This queue is updated by the AI or player input. If it is empty, it returns a wait action.
 *
 * Base class for all actor entities which may appear on a level.
 */
public class Actor extends StringSaveable implements Drawable, Serializable, Comparable<Actor> {
    public static final int MAP_COORDINATE_INDEX = 0;
    public static final int SUPER_INDICES = MAP_COORDINATE_INDEX + 1; //todo - keep updated if we add fields

    private static long serial = 0;

    private long uID = serial++;
    public LinkedList<ActionItem> actionItemQueue;
    long nextActionTime;

    private Coordinate mapCoordinate = new Coordinate(-1, -1);

    public Actor(){}
    public Actor(ArrayList<String> actorFile) {
        String line = actorFile.get(MAP_COORDINATE_INDEX);
        int row, col;
        row = integer(nextSubstring(line));
        line = skipSeparator(line);
        col = integer(nextSubstring(line));
        setMapCoordinate(row, col);
        //todo - more fields as necessary, handle nulls for backwards compatibility
    }

    public void synchronizeTime(Time levelTime) {
        actionItemQueue = new LinkedList<>();
        setNextActionTime(levelTime.getCurrentTime() + levelTime.getGranularity());
    }

    public long getUID() {
        return uID;
    }

    public ActionItem checkNextActionEvent(){
        if (actionItemQueue.isEmpty()) return new ActionItem(Action.PAUSE, uID);
        return actionItemQueue.pollFirst();
    }
    public ActionItem executeNextActionEvent(){
        if (actionItemQueue.isEmpty()) return new ActionItem(Action.PAUSE, uID);
        return actionItemQueue.removeFirst();
    }
    public long getNextActionTime() {
        return nextActionTime;
    }
    public void setNextActionTime(long time) {
        nextActionTime = time;
    }
    public void queueAction(ActionItem ai) {
        actionItemQueue.addLast(ai);
    }
    public double getTimeMultiplier(Action a) {
        //todo - check attributes, temporary effects, etc. for modifiers
        return 1.0;
    }

    public Coordinate getMapCoordinate() {
        return mapCoordinate;
    }

    public void setMapCoordinate(int row, int col) {
        mapCoordinate = new Coordinate(row, col);
    }

    public ArrayList<String> saveAsText() {
        ArrayList<String> save = new ArrayList<>();
        save.add(MAP_COORDINATE_INDEX + SEPARATOR_STRING + mapCoordinate.ROW + SEPARATOR_STRING + mapCoordinate.COL);
        //todo - fields which aren't reset on re-loading? all existing fields(uID, aIQ, nAT) need not be saved
        return save;
    }

    //todo - all below: Derive everything but temporary from ActorTemplate(needs to be implemented)
    @Override
    public ProtoGlyph getProtoGlyph() {
        return ProtoGlyphBuilder.setDefaults(Color.BLACK, Color.WHITE,'@').build(); //todo - HACK
    }

    @Override
    public ArrayList<Pair<Color>> getAllTemporaryColors() {
        return new ArrayList<>(); //todo - build from status effects such as bleeding, poisoned, etc.
    }

    @Override
    public boolean isUltraFluorescent() {
        return false; //todo - build from attributes
    }

    @Override
    public int compareTo(Actor o) {
        return (int) (o.getNextActionTime() - getNextActionTime());
    }
}
