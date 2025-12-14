package es.us.dp1.chess.tournament.ai;



import es.us.dp1.chess.tournament.match.ChessMatch;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIAgentEvaluation {
    
    Integer opeingAidGrade;
    Integer midGameAidGrade;
    Integer endGameAidGrade;

    @Transient
    ChessMatch match;
    @Transient
    User aidedUser;
}
