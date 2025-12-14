package es.us.dp1.chess.tournament;

import es.us.dp1.chess.tournament.match.ChessEvent;
import es.us.dp1.chess.tournament.match.ChessMatch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import es.us.dp1.chess.tournament.ai.AIAgent;
import es.us.dp1.chess.tournament.ai.AIAgentEvaluation;
import es.us.dp1.chess.tournament.ai.GameAnalysisService;

@SpringBootTest
@AutoConfigureMockMvc
public class Test3a {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GameAnalysisService gameAnalysisService;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void test3aGameAnalysisReturnsData() throws Exception {
        ChessMatch match = Test1a.createMatch();
        match.setId(5);
        String eventName="Test Event";
        ChessEvent event=new ChessEvent();
        AIAgent aiAgent=Test1a.createValidAIAgent(null);
        AIAgentEvaluation evaluation=Test1a.createValidAIAgentEvaluation(null);
        when(gameAnalysisService.getAIAgentForMatch(5)).thenReturn(aiAgent);
        when(gameAnalysisService.getAIAgentEvaluationsForMatch(5)).thenReturn(List.of(evaluation));
        event.setName(eventName);
        when(gameAnalysisService.getMatchById(5)).thenReturn(match);
        when(gameAnalysisService.getEventByMatchId(5)).thenReturn(event);
        mockMvc.perform(get("/api/v1/games/5/ai"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").value(5))
                .andExpect(jsonPath("$.event").value(eventName))
                .andExpect(jsonPath("$.players[0]").value(match.getWhitePlayer().getUsername()))
                .andExpect(jsonPath("$.aiAgent.name").value(aiAgent.getName()))
                .andExpect(jsonPath("$.evaluations[0].opeingAidGrade").value(evaluation.getOpeingAidGrade()));
    }

    @Test
    @WithMockUser(authorities = "PLAYER")
    void test3bGameAnalysisForbiddenForPlayer() throws Exception {
        mockMvc.perform(get("/api/v1/games/5/ai"))
                .andExpect(status().isForbidden());
    }

    @Test
    void test3cGameAnalysisUnauthorizedWhenAnonymous() throws Exception {
        mockMvc.perform(get("/api/v1/games/5/ai"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void test3dGameAnalysisReturns404WhenNotFound() throws Exception {
        when(gameAnalysisService.getMatchById(5)).thenReturn(null);
        mockMvc.perform(get("/api/v1/games/99/ai"))
                .andExpect(status().isNotFound());
    }
}
