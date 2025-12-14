package es.us.dp1.chess.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.chess.tournament.ai.AIAgent;
import es.us.dp1.chess.tournament.ai.AIAgentEvaluation;
import es.us.dp1.chess.tournament.ai.AIAgentType;
import jakarta.persistence.EntityManager;

@DataJpaTest()
public class Test2 extends ReflexiveTest {

    @Autowired
    EntityManager em;

    // ---------- IA Agents: valores basicos ----------

    @Test
    public void test2aInitialAIAgent1() {
        AIAgent a1 = em.find(AIAgent.class, 1);
        assertNotNull(a1, "There should exist an AIAgent with id:1");

        assertEquals("DeepBlue", a1.getName());
        assertEquals("https://www.ibm.com/history/deep-blue", a1.getUrl());
        assertEquals(AIAgentType.EXPERT_SYSTEM, a1.getType());
        assertEquals(0.5, a1.getCostPerMatch());
    }

    @Test
    public void test2aInitialAIAgent2() {
        AIAgent a2 = em.find(AIAgent.class, 2);
        assertNotNull(a2, "There should exist an AIAgent with id:2");

        assertEquals("StockFish", a2.getName());
        assertEquals("https://stockfishchess.org", a2.getUrl());
        assertEquals(AIAgentType.RULE_BASED_SYSTEM, a2.getType());
        assertEquals(0.0, a2.getCostPerMatch());
    }

    // ---------- IA Agents: relaciones con ChessMatch ----------

    @Test
    public void test2bAIAgentLinkedMatches() {
        String matchesGetter = "getMatches";

        checkContainsById(AIAgent.class, 1, matchesGetter, 5, em);        
        checkContainsById(AIAgent.class, 2, matchesGetter, 6, em);        
    }

    // ---------- AIAgentEvaluations: valores basicos ----------

    @Test
    public void test2cInitialAIAgentEval1() {
        AIAgentEvaluation e1 = em.find(AIAgentEvaluation.class, 1);
        assertNotNull(e1, "There should exist an AIAgentEvaluation with id:1");

        assertEquals(6, e1.getOpeingAidGrade());
        assertEquals(8, e1.getMidGameAidGrade());
        assertEquals(3, e1.getEndGameAidGrade());
    }

    @Test
    public void test2cInitialAIAgentEval2() {
        AIAgentEvaluation e2 = em.find(AIAgentEvaluation.class, 2);
        assertNotNull(e2, "There should exist an AIAgentEvaluation with id:2");

        assertEquals(9, e2.getOpeingAidGrade());
        assertEquals(6, e2.getMidGameAidGrade());
        assertEquals(8, e2.getEndGameAidGrade());
    }

    // ---------- AIAgentEvaluations: relaciones con Match y User ----------

    @Test
    public void test2dAIAgentEvaluationsLinks() {
        String matchGetter = "getMatch";
        String aidedUserGetter = "getAidedUser";

        checkLinkedById(AIAgentEvaluation.class, 1, matchGetter, 5, em);
        checkLinkedById(AIAgentEvaluation.class, 1, aidedUserGetter, 4, em);

        checkLinkedById(AIAgentEvaluation.class, 2, matchGetter, 5, em);
        checkLinkedById(AIAgentEvaluation.class, 2, aidedUserGetter, 5, em);
    }
}
