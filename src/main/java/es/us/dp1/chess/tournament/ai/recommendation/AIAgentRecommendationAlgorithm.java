package es.us.dp1.chess.tournament.ai.recommendation;

import es.us.dp1.chess.tournament.ai.AIAgentType;
import es.us.dp1.chess.tournament.match.ChessMatchRepository;

public abstract class AIAgentRecommendationAlgorithm {
    ChessMatchRepository repo;

    public abstract AIAgentType recommendAIAgentForUser(Integer userId);

    public void setRepo(ChessMatchRepository rep){
        this.repo=rep;
    }
}
