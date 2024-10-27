package org.godniakedyta.game;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter(AccessLevel.PACKAGE)
public class InMemoryGameStorage implements GameStorage {
    private final Map<Rivals, Game> games = new HashMap<>();

    @Override
    public void add(Game game) {
        games.put(game.getRivals(), game);
    }

    @Override
    public void delete(Rivals rivals) {
        games.remove(rivals);
    }

    @Override
    public Optional<Game> findGameByRivals(Rivals rivals) {
        return Optional.ofNullable(games.get(rivals));
    }

    @Override
    public void update(TeamsScore teamsScore) {
        Rivals rivals = new Rivals(teamsScore.homeTeam(), teamsScore.awayTeam());
        Game game = findGameByRivals(rivals)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Game updatedGame = Game.builder()
                .rivals(game.getRivals())
                .homeTeamScore(teamsScore.homeTeamScore())
                .awayTeamScore(teamsScore.awayTeamScore())
                .totalScore(teamsScore.homeTeamScore() + teamsScore.awayTeamScore())
                .startTime(game.getStartTime())
                .build();
        games.remove(rivals);
        games.put(rivals, updatedGame);
    }

    @Override
    public List<Game> findGamesInProgress() {
        return games.values()
                .stream()
                .sorted(new GameComparator())
                .toList();
    }
}
