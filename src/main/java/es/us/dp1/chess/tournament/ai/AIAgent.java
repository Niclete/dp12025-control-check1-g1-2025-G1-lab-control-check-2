package es.us.dp1.chess.tournament.ai;


import java.util.List;

import es.us.dp1.chess.tournament.match.ChessMatch;
import es.us.dp1.chess.tournament.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AIAgent extends BaseEntity {
    @NotNull
    @Size(min = 3, max = 50)
    @NotBlank
    String name;
    @NotNull
    @Size(min = 10)
    @NotBlank
    String url;
    @NotNull
    @PositiveOrZero
    Double costPerMatch;
    @NotNull
    @Enumerated(EnumType.STRING)
    AIAgentType type;

    @OneToMany
    List<ChessMatch> matches;
}
