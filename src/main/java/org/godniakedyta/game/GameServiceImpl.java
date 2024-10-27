package org.godniakedyta.game;

import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameStorage gameStorage;

    @Override
    public void start(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);
        validateGameNotStarted(homeTeam, awayTeam);

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
    }

    private void validateGameNotStarted(String homeTeam, String awayTeam) {
        Rivals rivals = new Rivals(homeTeam, awayTeam);
        if (gameStorage.findGameByRivals(rivals).isPresent()) {
            throw new RuntimeException("This game has already been started");
        }
    }

    @Override
    public void end(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);
        Rivals rivals = new Rivals(homeTeam, awayTeam);
        gameStorage.delete(rivals);
    }

    @Override
    public void score(TeamsScore teamsScore) {
        validateTeams(teamsScore.homeTeam(), teamsScore.awayTeam());
        validateScore(teamsScore.homeTeamScore(), teamsScore.awayTeamScore());
        gameStorage.update(teamsScore);
    }

    private void validateScore(int homeTeamScore, int awayTeamScore) {
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }

    @Override
    public List<GameDTO> findGamesInProgress() {
        return gameStorage.findGamesInProgress()
                .stream()
                .map(GameDTO::from)
                .toList();
    }
}
