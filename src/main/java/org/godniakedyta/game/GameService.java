package org.godniakedyta.game;

public interface GameService {
    void start(String homeTeam, String awayTeam);
    void end(String homeTeam, String awayTeam);
    void score(TeamsScore teamsScore);
}
