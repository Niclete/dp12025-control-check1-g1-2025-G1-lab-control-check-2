package es.us.dp1.chess.tournament.match;

import es.us.dp1.chess.tournament.model.NamedEntity;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChessMatch extends NamedEntity {
    LocalDateTime start;
    LocalDateTime finish;
    Long turnDuration;


    @ManyToOne
    User whitePlayer;

    @ManyToOne
    User blackPlayer;

    @ManyToOne
    User winner;

    @OneToOne(cascade = CascadeType.ALL)
    ChessBoard board;

}
