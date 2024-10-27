package org.godniakedyta.game;

public record GameDTO(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
    static GameDTO from(Game game) {
        return new GameDTO(game.getRivals().homeTeam(),
                game.getRivals().awayTeam(),
                game.getHomeTeamScore(),
                game.getAwayTeamScore());
    }
}
