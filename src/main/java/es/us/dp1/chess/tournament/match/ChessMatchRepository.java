package es.us.dp1.chess.tournament.match;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public interface ChessMatchRepository extends CrudRepository<ChessMatch,Integer>{

    @Query("SELECT -1 FROM ChessMatch m")
    Integer comprisedMatches(LocalDateTime start, LocalDateTime end);

    List<ChessMatch> findAll();

    @Query("SELECT cm,1 FROM ChessMatch cm")
    List<Object[]> findCapturePoints(Integer matchId);


    /**
     * Método de conveniencia que transforma la lista devuelta por JPQL
     * en un Map<PieceColor, Integer>, cumpliendo la firma pedida en el examen.
     */
    default Map<PieceColor, Integer> capturePoints(Integer matchId) {
        return findCapturePoints(matchId).stream()
                .collect(Collectors.toMap(
                        row -> (PieceColor) row[0],
                        row -> ((Number) row[1]).intValue()
                ));
    }


    @Query("SELECT cm FROM ChessMatch cm WHERE cm.whitePlayer.id = ?1 OR cm.blackPlayer.id = ?1")
    public List<ChessMatch> findByPlayerId(Integer userId);
}
