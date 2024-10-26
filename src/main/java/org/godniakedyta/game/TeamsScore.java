package org.godniakedyta.game;

import lombok.Builder;

@Builder
public record TeamsScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {}
