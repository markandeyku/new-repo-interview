package lld.cricinfo.state;


import lld.cricinfo.entity.Ball;
import lld.cricinfo.entity.Match;

public class FinishedState implements MatchState {
    @Override
    public void processBall(Match match, Ball ball) {
        System.out.println("ERROR: Cannot process a ball for a finished match.");
    }
}
