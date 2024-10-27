package org.godniakedyta.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class
InMemoryGameStorageTest {

    private InMemoryGameStorage gameStorage;

    @BeforeEach
    void setup() {
        gameStorage = new InMemoryGameStorage();
    }

    @Test
    void GIVEN_game_provided_WHEN_add_THEN_gamesContainsGame() {
        //given
        var game = validGame("Austria", "Belgium");
        //when
        gameStorage.add(game);
        //then
        assertEquals(1, gameStorage.getGames().size());
        assertEquals(game, gameStorage.getGames().get(game.getRivals()));
    }

    @Test
    void GIVEN_game_WHEN_delete_THEN_gamesDoesNotContainGame() {
        //given
        var rivals = new Rivals("Austria", "Belgium");
        var game = validGame("Austria", "Belgium");
        gameStorage.getGames().put(game.getRivals(), game);
        //when
        gameStorage.delete(rivals);
        //then
        assertEquals(0, gameStorage.getGames().size());
    }

    @Test
    void GIVEN_rivals_WHEN_findGameByRivals_THEN_gameIsReturned() {
        //given
        var rivals = new Rivals("Austria", "Belgium");
        var game = validGame("Austria", "Belgium");
        gameStorage.getGames().put(rivals, game);
        //when
        var foundGame = gameStorage.findGameByRivals(rivals);
        //then
        assertTrue(foundGame.isPresent());
        assertEquals(rivals, foundGame.get().getRivals());
    }

    @Test
    void GIVEN_teamsScore_WHEN_update_THEN_gameIsUpdated() {
        //given
        var rivals = new Rivals("Austria", "Belgium");
        var game = validGame("Austria", "Belgium");
        gameStorage.getGames().put(rivals, game);
        var teamsScore = TeamsScore.builder()
                .homeTeam("Austria")
                .awayTeam("Belgium")
                .homeTeamScore(1)
                .awayTeamScore(1)
                .build();
        //when
        gameStorage.update(teamsScore);
        //then
        assertEquals(1, gameStorage.getGames().get(rivals).getHomeTeamScore());
        assertEquals(1, gameStorage.getGames().get(rivals).getAwayTeamScore());
        assertEquals(2, gameStorage.getGames().get(rivals).getTotalScore());
    }

    @Test
    void GIVEN_game_WHEN_findGamesInProgress_THEN_returns_game() {
        //given
        var rivals1 = new Rivals("Austria", "Belgium");
        var game1 = validGame("Austria", "Belgium");
        gameStorage.getGames().put(rivals1, game1);
        //when
        List<Game> gamesInProgress = gameStorage.findGamesInProgress();
        //then
        assertEquals(1, gamesInProgress.size());
        assertTrue(gamesInProgress.contains(game1));
    }

    @Test
    void GIVEN_two_games_with_same_score_and_different_start_time_WHEN_findGamesInProgress_THEN_returns_games_sorted_by_recent_time() {
        //given
        var rivals1 = new Rivals("Austria", "Belgium");
        var game1 = Game.builder()
                .rivals(rivals1)
                .homeTeamScore(0)
                .awayTeamScore(1)
                .totalScore(1)
                .startTime(ZonedDateTime.now().minusMinutes(10))
                .build();
        gameStorage.getGames().put(rivals1, game1);
        var rivals2 = new Rivals("Poland", "Germany");
        var game2 = Game.builder()
                .rivals(rivals2)
                .homeTeamScore(0)
                .awayTeamScore(1)
                .totalScore(1)
                .startTime(ZonedDateTime.now())
                .build();
        gameStorage.getGames().put(rivals2, game2);
        //when
        List<Game> gamesInProgress = gameStorage.findGamesInProgress();
        //then
        assertEquals(2, gamesInProgress.size());
        assertEquals(game2, gamesInProgress.get(0));
        assertEquals(game1, gamesInProgress.get(1));
    }

    private Game validGame(String homeTeam, String awayTeam) {
        return Game.builder()
                .rivals(new Rivals(homeTeam, awayTeam))
                .homeTeamScore(0)
                .awayTeamScore(1)
                .totalScore(1)
                .startTime(ZonedDateTime.now())
                .build();
    }
}