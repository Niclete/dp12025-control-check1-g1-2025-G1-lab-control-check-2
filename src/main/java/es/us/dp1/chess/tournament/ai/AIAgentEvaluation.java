package es.us.dp1.chess.tournament.ai;



import org.hibernate.validator.constraints.Range;

import es.us.dp1.chess.tournament.match.ChessMatch;
import es.us.dp1.chess.tournament.model.BaseEntity;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AIAgentEvaluation extends BaseEntity {

    @NotNull
    @Range(min = 0, max = 10)
    Integer opeingAidGrade;
    @NotNull
    @Range(min = 0, max = 10)
    Integer midGameAidGrade;
    @NotNull
    @Range(min = 0, max = 10)
    Integer endGameAidGrade;

    @ManyToOne
    @NotNull
    ChessMatch match;
    @ManyToOne
    @NotNull
    User aidedUser;
}
