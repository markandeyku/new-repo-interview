package lld.cricinfo.state;

import lld.cricinfo.entity.Ball;
import lld.cricinfo.entity.Match;

public class ScheduledState implements MatchState {
    @Override
    public void processBall(Match match, Ball ball) {
        System.out.println("ERROR: Cannot process a ball for a match that has not started.");
    }
}
