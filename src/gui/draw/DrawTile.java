package gui.draw;

import graph.Direction;

public class DrawTile {
    boolean remembered = false;
    LightSight[] litSeen = new LightSight[9];

    public DrawTile(Light ambientLight){
        for (int i = 0; i < litSeen.length; ++i) litSeen[i] = new LightSight();
        lightFrom(Direction.SELF, ambientLight);
    }

    private int indexOf(Direction direction) {
        return direction.ordinal();
    }

    public void lightFrom(Direction d, Light l) {
        if (l.compareTo(litSeen[indexOf(d)].getLight()) > 0)
            litSeen[(indexOf(d))].setLight(l);
    }
    public void seeFrom(Direction d, short sightPower) {
        remembered = true;
        if (sightPower > litSeen[indexOf(d)].getSight()) {
            litSeen[indexOf(d)].setSight(sightPower);
            if (sightPower > litSeen[indexOf(Direction.SELF)].getSight())
                litSeen[indexOf(Direction.SELF)].setSight(sightPower);
        }
    }
    public Light lightFrom(Direction d) {
        return litSeen[indexOf(d)].getLight();
    }
    public short sightFrom(Direction d) {
        return litSeen[indexOf(d)].getSight();
    }
}
