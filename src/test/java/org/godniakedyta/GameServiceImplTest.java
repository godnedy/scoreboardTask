package org.godniakedyta;

import org.godniakedyta.game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameServiceImplTest {

    private GameServiceImpl gameServiceImpl;
    private GameStorage gameStorage;

    @BeforeEach
    void setup() {
        gameStorage = Mockito.mock(GameStorage.class);
        gameServiceImpl = new GameServiceImpl(gameStorage);
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
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        var homeTeam = "Austria";
        var awayTeam = "Belgium";
        //when
        ZonedDateTime timeBeforeExecution = ZonedDateTime.now();
        gameServiceImpl.start(homeTeam, awayTeam);
        ZonedDateTime timeAfterExecution = ZonedDateTime.now();
        //then
        verify(gameStorage, times(1)).add(gameCaptor.capture());
        var gamePassed = gameCaptor.getValue();
        assertEquals(homeTeam, gamePassed.getRivals().homeTeam());
        assertEquals(awayTeam, gamePassed.getRivals().awayTeam());
        assertEquals(0, gamePassed.getHomeTeamScore());
        assertEquals(0, gamePassed.getAwayTeamScore());
        assertEquals(0, gamePassed.getTotalScore());
        assertTrue(timeBeforeExecution.isBefore(gamePassed.getStartTime()) && timeAfterExecution.isAfter(gamePassed.getStartTime()));
    }

    @ParameterizedTest(name = "{0} as homeTeam and {1} as awayTeam")
    //etc. this is not a final list of all edge cases
    @CsvSource({"Austria,Austria",
            "Austria,''",
            "'',''",
            "'',Austria",
            ",",
            "Austria,",
            ",Austria",
            "'',",
            "' ',Austria"})
    void GIVEN_wrongParams_WHEN_start_THEN_IllegalArgumentExpectionIsThrown(String homeTeam, String awayTeam) {
        //then
        assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.start(homeTeam, awayTeam));
    }

    @ParameterizedTest(name = "{0} as homeTeam and {1} as awayTeam")
    @CsvSource({"Austria,Belgium",
            "Belgium,Austria"})
    void GIVEN_allParamsProvided_WHEN_startAndGameAlreadyExists_THEN_UnsupportedOperationExceptionIsThrown(String homeTeam, String awayTeam) {
        //when
        when(gameStorage.findGameByRivals(any())).thenReturn(Optional.of(Game.builder().build()));
        //then
        assertThrows(RuntimeException.class, () -> gameServiceImpl.start(homeTeam, awayTeam));
    }

    @Test
    void GIVEN_allParamsProvided_WHEN_end_THEN_runWithoutExceptions() {
        //given
        var teamHome = "Austria";
        var teamAway = "Belgium";
        //when
        gameServiceImpl.end(teamHome, teamAway);
    }

    @Test
    void GIVEN_allParamsProvided_WHEN_end_THEN_gameDeletedFromStorage() {
        //given
        ArgumentCaptor<Rivals> rivalsCaptor = ArgumentCaptor.forClass(Rivals.class);
        var homeTeam = "Austria";
        var awayTeam = "Belgium";
        //when
        gameServiceImpl.end(homeTeam, awayTeam);
        //then
        verify(gameStorage, times(1)).delete(rivalsCaptor.capture());
        var rivals = rivalsCaptor.getValue();
        assertEquals(homeTeam, rivals.homeTeam());
        assertEquals(awayTeam, rivals.awayTeam());
    }

    @ParameterizedTest(name = "{0} as homeTeam and {1} as awayTeam")
    //etc. this is not a final list of all edge cases
    @CsvSource({"Austria,Austria",
            "Austria,''",
            "'',''",
            "'',Austria",
            ",",
            "Austria,",
            ",Austria",
            "'',",
            "' ',Austria"})
    void GIVEN_wrongParams_WHEN_end_THEN_IllegalArgumentExpectionIsThrown(String homeTeam, String awayTeam) {
        //then
        assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.end(homeTeam, awayTeam));
    }

    @Test
    void GIVEN_allParamsProvided_WHEN_score_THEN_runWithoutExceptions() {
        //given
        var teamsScore = TeamsScore.builder()
                .homeTeam("Austria")
                .awayTeam("Belgium")
                .homeTeamScore(0)
                .awayTeamScore(1)
                .build();
        //when
        gameServiceImpl.score(teamsScore);
    }

    @Test
    void GIVEN_allParamsProvided_WHEN_score_THEN_updatesGameInStorage() {
        //given
        ArgumentCaptor<TeamsScore> teamsScoreCaptor = ArgumentCaptor.forClass(TeamsScore.class);
        var teamsScore = TeamsScore.builder()
                .homeTeam("Austria")
                .awayTeam("Belgium")
                .homeTeamScore(0)
                .awayTeamScore(1)
                .build();
        //when
        gameServiceImpl.score(teamsScore);
        //then
        verify(gameStorage, times(1)).update(teamsScoreCaptor.capture());
        var capturedTeamsScore = teamsScoreCaptor.getValue();
        assertEquals(teamsScore, capturedTeamsScore);
    }

    @ParameterizedTest(name = "{0} as homeTeam and {1} as awayTeam")
    //etc. this is not a final list of all edge cases
    @CsvSource({"Austria,Austria",
            "Austria,''",
            "'',''",
            "'',Austria",
            ",",
            "Austria,",
            ",Austria",
            "'',",
            "' ',Austria"})
    void GIVEN_wrongTeams_WHEN_score_THEN_IllegalArgumentExpectionIsThrown(String homeTeam, String awayTeam) {
        //given
        var teamsScore = TeamsScore.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeTeamScore(0)
                .awayTeamScore(1)
                .build();
        //then
        assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.score(teamsScore));
    }

    @ParameterizedTest(name = "{0} as homeTeamScore and {1} as awayTeamScore")
    @CsvSource({"-1,0",
            "-1,-1",
            "0,-1",
            "1,-1",
            "-1, 1"})
    void GIVEN_negativeScore_WHEN_score_THEN_IllegalArgumentExpectionIsThrown(int homeTeamScore, int awayTeamScore) {
        //given
        var teamsScore = TeamsScore.builder()
                .homeTeam("Austria")
                .awayTeam("Belgium")
                .homeTeamScore(homeTeamScore)
                .awayTeamScore(awayTeamScore)
                .build();
        //then
        assertThrows(IllegalArgumentException.class, () -> gameServiceImpl.score(teamsScore));
    }

    @Test
    void WHEN_findGamesInProgess_THEN_runWithoutExceptions() {
        //when
        gameServiceImpl.findGamesInProgress();
    }

}