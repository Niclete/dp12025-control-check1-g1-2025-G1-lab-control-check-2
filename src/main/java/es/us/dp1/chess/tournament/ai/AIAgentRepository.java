package es.us.dp1.chess.tournament.ai;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AIAgentRepository extends CrudRepository<AIAgent, Integer> {
    Optional<AIAgent> findById(Integer id);
    @Query("SELECT a FROM AIAgent a JOIN a.matches m WHERE m.id = :matchId")
    AIAgent findByMatchId(Integer matchId);
}
