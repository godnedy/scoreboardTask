package org.godniakedyta.game;

import java.util.List;

public interface GameService {
    void start(String homeTeam, String awayTeam);
    void end(String homeTeam, String awayTeam);
    void score(TeamsScore teamsScore);
    List<GameDTO> findGamesInProgress();
}
