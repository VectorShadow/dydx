package graph;

import flag.Flag;
import level.Level;

import java.util.ArrayList;

public class Graph {
    ArrayList<SubGraph> subGraphs;

    /**
     * Level is the level used to generate this graph.
     * PrimaryFlag is the flag used to determine which vertices are included in this graph, and how to apply
     * the boolean condition.
     * SecondaryFlags is a list of additional flags which clients of this graph might wish to consider - for example,
     * in a movement graph, damage costs associated with passing tiles might be of more value to a particular AI than
     * the raw movement costs.
     *
     * Specify whether we want edges to be drawn from valid source tiles to any adjacent tile regardless of flag,
     * or whether the destination tile must have a matching flag to constitute a valid edge.
     *
     * For example, a light graph would draw edges from the a tile that allows light to all adjacent tiles, regardless
     * of whether they allow light, for the purpose of illuminating them, but would not draw edges from those tiles,
     * since the light will not pass them. But a movement graph will only draw edges between pairs of tiles that both
     * allow movement, since otherwise movement is not valid along that edge.
     */
    public Graph(Level level, Flag primaryFlag, ArrayList<Flag> secondaryFlags, boolean requireDestinationFlag){

    }
}
