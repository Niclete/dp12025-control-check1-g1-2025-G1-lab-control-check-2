package es.us.dp1.chess.tournament.ai.recommendation;

import java.util.List;

import org.springframework.stereotype.Component;

import es.us.dp1.chess.tournament.ai.AIAgentType;
import es.us.dp1.chess.tournament.match.ChessMatch;


@Component
public class StrangeTrainingRecommendationAlgorithm extends AIAgentRecommendationAlgorithm {    

    @Override
    public AIAgentType recommendAIAgentForUser(Integer userId) {
        List<ChessMatch> partidas = repo.findByPlayerId(userId);
        int partidasGanadas = partidas.stream()
                .filter(partida -> partida.getWinner() != null && partida.getWinner().getId().equals(userId))
                .toList().size();
        int partidasPerdidas = partidas.stream()
                .filter(partida -> partida.getWinner() != null && !partida.getWinner().getId().equals(userId))
                .toList().size();
        double porcentajeGanadas = partidasGanadas/(double)(partidasGanadas + partidasPerdidas);
        if (porcentajeGanadas>=50.0) {
            return AIAgentType.LARGE_LANGUAGE_MODEL;
        } else if (porcentajeGanadas>=10.0) {
            return AIAgentType.EXPERT_SYSTEM;
        }else
            return null;
    }

}
