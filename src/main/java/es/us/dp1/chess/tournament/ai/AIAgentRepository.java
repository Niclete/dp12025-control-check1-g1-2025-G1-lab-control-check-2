package es.us.dp1.chess.tournament.ai;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

public interface AIAgentRepository {
    Optional<AIAgent> findById(Integer id);
    @Query("SELECT a FROM AIAgent a WHERE a.id = :matchId")
    AIAgent findByMatchId(Integer matchId);
}
