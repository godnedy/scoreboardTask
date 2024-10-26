package org.godniakedyta.game;

import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class Game {
    private final Rivals rivals;
    private final int homeTeamScore;
    private final int awayTeamScore;
    private final int totalScore;
    private final ZonedDateTime startTime;
}
