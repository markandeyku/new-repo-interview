package lld.cricinfo.observer;


import lld.cricinfo.entity.Ball;
import lld.cricinfo.entity.Match;

public interface MatchObserver {
    void update(Match match, Ball lastBall);
}
