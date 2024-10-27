package org.godniakedyta.game;

import java.util.Optional;

public interface GameStorage {
    void add(Game game);
    void delete(Rivals rivals);
    Optional<Game> findGameByRivals(Rivals rivals);
    void update(TeamsScore teamsScore);
}
