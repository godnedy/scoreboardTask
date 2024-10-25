package org.godniakedyta;

import org.godniakedyta.game.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceImplTest {

    private GameServiceImpl gameServiceImpl;

    @BeforeEach
    void setup() {
        gameServiceImpl = new GameServiceImpl();
    }

    @Test
    void GIVEN_allParamsProvided_WHEN_start_THEN_runWithoutExceptions() {
        //given
        String teamHome = "Austria";
        String teamAway = "Belgium";
        //when
        gameServiceImpl.start(teamHome, teamAway);
    }

}