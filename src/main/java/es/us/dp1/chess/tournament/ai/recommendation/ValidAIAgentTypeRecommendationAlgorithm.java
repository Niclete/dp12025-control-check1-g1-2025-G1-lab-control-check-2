package es.us.dp1.chess.tournament.ai.recommendation;

import java.util.List;

import org.springframework.stereotype.Component;

import es.us.dp1.chess.tournament.ai.AIAgentType;
import es.us.dp1.chess.tournament.match.ChessMatch;


@Component
public class ValidAIAgentTypeRecommendationAlgorithm extends AIAgentRecommendationAlgorithm {
    

    @Override
    public AIAgentType recommendAIAgentForUser(Integer userId) {
        List<ChessMatch> partidas = repo.findByPlayerId(userId);
        long partidasGanadas = partidas.stream()
                .filter(partida -> partida.getWinner() != null && partida.getWinner().getId().equals(userId))
                .count();
        long partidasPerdidas = partidas.stream()
                .filter(partida -> partida.getWinner() == null || !partida.getWinner().getId().equals(userId))
                .count();

        long totalPartidas = partidasGanadas + partidasPerdidas;
        if (totalPartidas == 0) {
            return AIAgentType.LARGE_LANGUAGE_MODEL;
        }        

        double porcentajeGanadas = partidasGanadas / (double) totalPartidas;
        if (porcentajeGanadas >= 0.5) {
            return null;
        } else if(porcentajeGanadas >= 0.15) {
            return AIAgentType.RULE_BASED_SYSTEM;
        } else {
            return AIAgentType.EXPERT_SYSTEM;
        }
    }

}
