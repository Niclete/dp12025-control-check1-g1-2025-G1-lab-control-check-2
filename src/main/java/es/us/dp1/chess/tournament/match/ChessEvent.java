package es.us.dp1.chess.tournament.match;

import es.us.dp1.chess.tournament.model.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class ChessEvent extends NamedEntity {
    LocalDateTime start;
    LocalDateTime finish;
    String location;

    @OneToMany
    Set<ChessMatch> matches;
}
