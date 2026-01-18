package es.us.dp1.chess.tournament.ai;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AIAgentEvaluationRepository extends CrudRepository<AIAgentEvaluation, Integer> {
    Optional<AIAgentEvaluation> findById(Integer id);
    @Query("SELECT a FROM AIAgentEvaluation a WHERE a.match.id = :matchId")
    List<AIAgentEvaluation> findByMatchId(Integer matchId);
}
