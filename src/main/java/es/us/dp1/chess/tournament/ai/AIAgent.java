package es.us.dp1.chess.tournament.ai;


import java.util.List;

import es.us.dp1.chess.tournament.match.ChessMatch;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIAgent {
    String name;
    String url;
    Double costPerMatch;
    AIAgentType type;

    @Transient
    List<ChessMatch> matches;
}
