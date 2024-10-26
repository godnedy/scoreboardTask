package org.godniakedyta.game;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameStorage gameStorage;

    @Override
    public void start(String homeTeam, String awayTeam) {

    }

    @Override
    public void end(String homeTeam, String awayTeam) {

    }

    @Override
    public void score(int homeTeamScore, int awayTeamScore) {

    }
}
