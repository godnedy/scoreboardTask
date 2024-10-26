package org.godniakedyta.game;

import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameStorage gameStorage;

    @Override
    public void start(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);

        Game newGame = Game.builder()
                .rivals(new Rivals(homeTeam, awayTeam))
                .homeTeamScore(0)
                .awayTeamScore(0)
                .totalScore(0)
                .startTime(ZonedDateTime.now())
                .build();
        gameStorage.add(newGame);
    }

    private void validateTeams(String homeTeam, String awayTeam) {
        if (homeTeam == null
                || homeTeam.isBlank()
                || awayTeam == null
                || awayTeam.isBlank()
                || homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("You must specify both teams as not empty and non equal strings");
        }
        Rivals rivals = new Rivals(homeTeam, awayTeam);
        if (gameStorage.findGameByRivals(rivals).isPresent()) {
            throw new UnsupportedOperationException("This game has already been started");
        }
    }

    @Override
    public void end(String homeTeam, String awayTeam) {
        Rivals rivals = new Rivals(homeTeam, awayTeam);
        gameStorage.delete(rivals);
    }

    @Override
    public void score(int homeTeamScore, int awayTeamScore) {

    }
}
