package org.godniakedyta.game;

import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameStorage gameStorage;

    @Override
    public void start(String homeTeam, String awayTeam) {
        Game newGame = Game.builder()
                .rivals(new Rivals(homeTeam, awayTeam))
                .homeTeamScore(0)
                .awayTeamScore(0)
                .totalScore(0)
                .startTime(ZonedDateTime.now())
                .build();
        gameStorage.add(newGame);
    }

    @Override
    public void end(String homeTeam, String awayTeam) {

    }

    @Override
    public void score(int homeTeamScore, int awayTeamScore) {

    }
}
