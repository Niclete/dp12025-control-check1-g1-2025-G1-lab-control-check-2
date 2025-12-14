package es.us.dp1.chess.tournament.recommendation;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

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

    // Please specify as many tests as you need using structures similar to this:
    @Test
    public void someTest(){
        // TODO: Remove next line and write your code here
        fail();
        // Arrangement / Configuration /Fixture
        // Act / SUT invocation
        //Assertion:
    }



    public void setAlgorithm(AIAgentRecommendationAlgorithm value){
        this.algorithm=value;
    }
}
