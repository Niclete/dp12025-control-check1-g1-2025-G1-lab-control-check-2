package es.us.dp1.chess.tournament.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.chess.tournament.ai.AIAgentType;
import es.us.dp1.chess.tournament.ai.recommendation.AIAgentRecommendationAlgorithm;

public class AIAgentTypeRecommendationAlgorithmTest {

/*  SUMMARY OF PLAYERS AND THEIR MATCHES (ACCORDING TO THE INITIAL DATASET):
 =========================================================================================
 ID  | Username | Matches Played    | Matches Won       | Matches Lost      | Win Ratio %
=========================================================================================
  4  | player1  |        7          |         1         |         6         |   14.29 %
-----------------------------------------------------------------------------------------
  5  | player2  |        7          |         2         |         5         |   28.57 %
-----------------------------------------------------------------------------------------
  6  | player3  |        4          |         3         |         1         |   75.00 %
-----------------------------------------------------------------------------------------
 7–13| player4–player10 
     |        0          |         0         |         0         |    0.00 %  (no han jugado)
========================================================================================= 
 * 
 * 
 */

    // This is your SUT:
    AIAgentRecommendationAlgorithm algorithm=null;

    @BeforeEach
    public void ensureAlgorithmIsProvided() {
        assumeTrue(algorithm != null, "Algorithm must be provided by the test harness");
    }

    @Test
    public void recommendsLargeLanguageModelWhenNoMatchesPlayed() {
        AIAgentType recommendation = algorithm.recommendAIAgentForUser(7);
        assertEquals(AIAgentType.LARGE_LANGUAGE_MODEL, recommendation,
            "Users with no matches should be recommended a LARGE_LANGUAGE_MODEL training");
    }

    @Test
    public void recommendsExpertSystemWhenWinRateBelowFifteenPercent() {
        AIAgentType recommendation = algorithm.recommendAIAgentForUser(4);
        assertEquals(AIAgentType.EXPERT_SYSTEM, recommendation,
            "Users with less than 15% wins should be recommended EXPERT_SYSTEM");
    }

    @Test
    public void recommendsRuleBasedSystemBetweenFifteenAndFiftyPercent() {
        AIAgentType recommendation = algorithm.recommendAIAgentForUser(5);
        assertEquals(AIAgentType.RULE_BASED_SYSTEM, recommendation,
            "Users with win rate between 15% and 49.9% should be recommended RULE_BASED_SYSTEM");
    }

    @Test
    public void returnsNullWhenWinsAtLeastLosses() {
        AIAgentType recommendation = algorithm.recommendAIAgentForUser(6);
        assertNull(recommendation,
            "Users with wins equal to or greater than losses should not receive a recommendation");
    }



    public void setAlgorithm(AIAgentRecommendationAlgorithm value){
        this.algorithm=value;
    }
}
