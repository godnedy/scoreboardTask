package org.godniakedyta;

import org.godniakedyta.game.Game;
import org.godniakedyta.game.GameServiceImpl;
import org.godniakedyta.game.GameStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GameServiceImplTest {

    private GameServiceImpl gameServiceImpl;
    private GameStorage gameStorage;
    private ArgumentCaptor<Game> gameCaptor;

    @BeforeEach
    void setup() {
        gameStorage = Mockito.mock(GameStorage.class);
        gameServiceImpl = new GameServiceImpl(gameStorage);
        gameCaptor = ArgumentCaptor.forClass(Game.class);
    }

    @Test
    void GIVEN_allParamsProvided_WHEN_start_THEN_runWithoutExceptions() {
        //given
        var teamHome = "Austria";
        var teamAway = "Belgium";
        //when
        gameServiceImpl.start(teamHome, teamAway);
    }

    @Test
    void GIVEN_allParamsProvided_WHEN_start_THEN_newGameAddedToStorage() {
        //given
        var teamHome = "Austria";
        var teamAway = "Belgium";
        //when
        ZonedDateTime timeBeforeExecution = ZonedDateTime.now();
        gameServiceImpl.start(teamHome, teamAway);
        ZonedDateTime timeAfterExecution = ZonedDateTime.now();
        //then
        verify(gameStorage, times(1)).add(gameCaptor.capture());
        var gamePassed = gameCaptor.getValue();
        assertEquals(teamHome, gamePassed.getRivals().homeTeam());
        assertEquals(teamAway, gamePassed.getRivals().awayTeam());
        assertEquals(0, gamePassed.getHomeTeamScore());
        assertEquals(0, gamePassed.getAwayTeamScore());
        assertEquals(0, gamePassed.getTotalScore());
        assertTrue(timeBeforeExecution.isBefore(gamePassed.getStartTime()) && timeAfterExecution.isAfter(gamePassed.getStartTime()));
    }

}