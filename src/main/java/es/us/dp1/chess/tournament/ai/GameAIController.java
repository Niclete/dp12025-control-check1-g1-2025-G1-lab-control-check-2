package es.us.dp1.chess.tournament.ai;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.chess.tournament.match.ChessEvent;
import es.us.dp1.chess.tournament.match.ChessMatch;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/games")
@SecurityRequirement(name = "bearerAuth")
public class GameAIController {

    private final GameAnalysisService gameAnalysisService;

    public GameAIController(GameAnalysisService gameAnalysisService) {
        this.gameAnalysisService = gameAnalysisService;
    }

    @GetMapping("/{gameId}/ai")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('COACH')")
    public ResponseEntity<GameAIResponse> getGameAIData(@PathVariable Integer gameId) {
        ChessMatch match = gameAnalysisService.getMatchById(gameId);
        if (match == null) {
            return ResponseEntity.notFound().build();
        }

        ChessEvent event = gameAnalysisService.getEventByMatchId(gameId);
        String eventName = event != null ? event.getName() : null;
        List<String> players = List.of(match.getWhitePlayer().getUsername(), match.getBlackPlayer().getUsername());
        AIAgent aiAgent = gameAnalysisService.getAIAgentForMatch(gameId);
        List<AIAgentEvaluation> evaluations = gameAnalysisService.getAIAgentEvaluationsForMatch(gameId);

        return ResponseEntity.ok(new GameAIResponse(match.getId(), eventName, players, aiAgent, evaluations));
    }

    static class GameAIResponse {
        private final Integer gameId;
        private final String event;
        private final List<String> players;
        private final AIAgent aiAgent;
        private final List<AIAgentEvaluation> evaluations;

        GameAIResponse(Integer gameId, String event, List<String> players, AIAgent aiAgent,
                List<AIAgentEvaluation> evaluations) {
            this.gameId = gameId;
            this.event = event;
            this.players = players;
            this.aiAgent = aiAgent;
            this.evaluations = evaluations;
        }

        public Integer getGameId() {
            return gameId;
        }

        public String getEvent() {
            return event;
        }

        public List<String> getPlayers() {
            return players;
        }

        public AIAgent getAiAgent() {
            return aiAgent;
        }

        public List<AIAgentEvaluation> getEvaluations() {
            return evaluations;
        }
    }
}