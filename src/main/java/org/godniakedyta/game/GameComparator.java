package org.godniakedyta.game;

import java.util.Comparator;

public class GameComparator implements Comparator<Game> {
    @Override
    public int compare(Game o1, Game o2) {
        int comparisonResult = Integer.compare(o2.getTotalScore(), o1.getTotalScore());
        if (comparisonResult != 0) {
            return comparisonResult;
        } else {
            return o2.getStartTime().compareTo(o1.getStartTime());
        }
    }
}
