package es.us.dp1.chess.tournament.ai.recommendation;

import es.us.dp1.chess.tournament.ai.AIAgentType;

public class CrappyTrainingRecommendationAlgorithm extends AIAgentRecommendationAlgorithm {

    @Override
    public AIAgentType recommendAIAgentForUser(Integer userId) {
        return AIAgentType.EXPERT_SYSTEM;
    }
}
