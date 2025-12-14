package es.us.dp1.chess.tournament.ai;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

public interface AIAgentEvaluationRepository {
    Optional<AIAgentEvaluation> findById(Integer id);
    @Query("SELECT a FROM AIAgentEvaluation a WHERE a.id = :id")
    List<AIAgentEvaluation> findByMatchId(Integer id);
}
