package lld.cricinfo.state;

import lld.cricinfo.entity.Ball;
import lld.cricinfo.entity.Match;

public interface MatchState {
    void processBall(Match match, Ball ball);

    default void startNextInnings(Match match) {
        System.out.println("ERROR: Cannot start the next innings from the current state.");
    }
}
