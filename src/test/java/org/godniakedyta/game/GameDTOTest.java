package org.godniakedyta.game;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GameDTOTest {

    @Test
    void GIVEN_game_entity_WHEN_from_THEN_gameDTOIsReturned() {
        //given
        var now = ZonedDateTime.now();
        var game = Game.builder()
                .rivals(new Rivals("Austria", "Belgium"))
                .homeTeamScore(0)
                .awayTeamScore(1)
                .startTime(now)
                .build();
        //when
        var gameDTO = GameDTO.from(game);
        //then
        assertNotNull(gameDTO);
        assertEquals("Austria", gameDTO.homeTeam());
        assertEquals("Belgium", gameDTO.awayTeam());
        assertEquals(0, gameDTO.homeTeamScore());
        assertEquals(1, gameDTO.awayTeamScore());
    }
}