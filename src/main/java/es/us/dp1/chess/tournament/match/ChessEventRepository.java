package es.us.dp1.chess.tournament.match;

import org.springframework.data.repository.CrudRepository;

public interface ChessEventRepository extends CrudRepository<ChessEvent, Integer>   {
    public ChessEvent findByMatches_Id(Integer matchId);
}
