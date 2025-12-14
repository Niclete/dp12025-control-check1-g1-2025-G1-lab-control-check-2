package es.us.dp1.chess.tournament.match;


import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Tournament {
    String name;
    String location;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer maxParticipants;

    @Transient
    List<ChessMatch> matches;
}
