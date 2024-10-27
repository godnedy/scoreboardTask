package org.godniakedyta.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GameComparatorTest {

    private GameComparator gameComparator;

    @BeforeEach
    void setup() {
        gameComparator = new GameComparator();
    }

    @Test
    void GIVEN_two_games_with_different_total_score_WHEN_compare_THEN_return_positive() {
        //given
        var now = ZonedDateTime.now();
        var game1 = Game.builder()
                .rivals(new Rivals("Austria", "Belgium"))
                .homeTeamScore(0)
                .awayTeamScore(1)
                .totalScore(1)
                .startTime(now)
                .build();
        var game2 = Game.builder()
                .rivals(new Rivals("Poland", "Germany"))
                .homeTeamScore(1)
                .awayTeamScore(1)
                .totalScore(2)
                .startTime(now)
                .build();
        //when
        int result = gameComparator.compare(game1, game2);
        //then
        assertEquals(1, result);
    }

    @Test
    void GIVEN_two_games_with_same_total_score_and_different_start_time_WHEN_compare_THEN_return_positive() {
        //given
        var now = ZonedDateTime.now();
        var game1 = Game.builder()
                .rivals(new Rivals("Austria", "Belgium"))
                .homeTeamScore(1)
                .awayTeamScore(1)
                .totalScore(2)
                .startTime(now.minusMinutes(10))
                .build();
        var game2 = Game.builder()
                .rivals(new Rivals("Poland", "Germany"))
                .homeTeamScore(1)
                .awayTeamScore(1)
                .totalScore(2)
                .startTime(now)
                .build();
        //when
        int result = gameComparator.compare(game1, game2);
        //then
        assertEquals(1, result);
    }
}