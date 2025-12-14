package es.us.dp1.chess.tournament.match;

import es.us.dp1.chess.tournament.model.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Federation extends NamedEntity {

    @OneToMany
    Set<ChessEvent> organizedEvents;
}
