package es.us.dp1.chess.tournament;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.chess.tournament.analysis.GameAnalysisRepository;
import es.us.dp1.chess.tournament.match.Federation;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.EntityManager;

@DataJpaTest
public class Test4 extends ReflexiveTest {

    @Autowired(required = false)
    private GameAnalysisRepository gameRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void test4FastestPlayersReturned() {
        assertNotNull(gameRepository, "GameRepository should be available for querying fastest winners");

        Federation fed1 = entityManager.find(Federation.class, 1);
        Federation fed2 = entityManager.find(Federation.class, 2);
        assertNotNull(fed1, "Federation 1 should exist in initial data");
        assertNotNull(fed2, "Federation 2 should exist in initial data");
        

        List<User> fastestFed1 = gameRepository.findMostWinners(
            Set.of(fed1),
            LocalDateTime.of(2025, 2, 1, 0, 0, 0)
        );

        List<Integer> fastestFed1Ids = fastestFed1.stream()
            .map(User::getId)
            .toList();

        assertEquals(List.of(6,4), fastestFed1Ids,
            "In federation 1 from Feb 2025, there are no winners...");

        List<User> fastestBothFederations = gameRepository.findMostWinners(
            Set.of(fed1, fed2),
            LocalDateTime.of(2025, 2, 1, 0, 0, 0)
        );

        List<Integer> fastestBothIds = fastestBothFederations.stream()
            .map(User::getId)
            .toList();

        assertEquals(List.of(6, 4, 5), fastestBothIds,
            "With both federations from Feb 2025, the winners should be 4, 5 and 6 ordered by number of victories");

        List<User> fastestOnlyFed2 = gameRepository.findMostWinners(
            Set.of(fed2),
            LocalDateTime.of(2025, 2, 1, 0, 0, 0)
        );

        assertEquals(1, fastestOnlyFed2.size(),
            "Federation 2 from Feb 2025 should only include the winner of match 305");
        assertEquals(5, fastestOnlyFed2.get(0).getId(),
            "Player 5 should be the only winner for federation 2 from 2025-02-01");

        List<User> noRecentMatches = gameRepository.findMostWinners(
            Set.of(fed1, fed2),
            LocalDateTime.of(2030, 1, 1, 0, 0, 0)
        );

        assertTrue(noRecentMatches.isEmpty(),
            "When filtering from a future date, no winners should be returned");
    }
}
