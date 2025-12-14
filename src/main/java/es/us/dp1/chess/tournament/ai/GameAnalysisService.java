package es.us.dp1.chess.tournament.ai;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.chess.tournament.exceptions.ResourceNotFoundException;
import es.us.dp1.chess.tournament.match.ChessEvent;
import es.us.dp1.chess.tournament.match.ChessEventRepository;
import es.us.dp1.chess.tournament.match.ChessMatch;
import es.us.dp1.chess.tournament.match.ChessMatchRepository;

@Service
public class GameAnalysisService {

    private final ChessMatchRepository matchRepository;
    private final AIAgentRepository aIAgentRepository;
    private final AIAgentEvaluationRepository aIAgentEvaluationRepository;
    private final ChessEventRepository chessEventRepository;

    @Autowired
    public GameAnalysisService(ChessMatchRepository matchRepository, Optional<AIAgentRepository> aiAgentRepository,
            Optional<AIAgentEvaluationRepository> aiAgentEvaluationRepository,ChessEventRepository chessEventRepository) {
        this.matchRepository = matchRepository;
        this.aIAgentRepository = aiAgentRepository.orElse(null);
        this.aIAgentEvaluationRepository = aiAgentEvaluationRepository.orElse(null);
        this.chessEventRepository = chessEventRepository;
    }


    public ChessMatch getMatchById(Integer matchId) {
        Optional<ChessMatch> matchOpt = matchRepository.findById(matchId);
        if (matchOpt.isEmpty()) {
            throw new ResourceNotFoundException("Match with id " + matchId + " not found");
        }
        return matchOpt.get();
    }
    @Transactional(readOnly = true)
    public AIAgent getAIAgentForMatch(Integer id) {
        return aIAgentRepository.findByMatchId(id);
    }

    @Transactional(readOnly = true)
    public List<AIAgentEvaluation> getAIAgentEvaluationsForMatch(Integer id) {
        return aIAgentEvaluationRepository.findByMatchId(id);
    }

    @Transactional(readOnly = true)
    public ChessEvent getEventByMatchId(Integer matchId) {
        return chessEventRepository.findByMatches_Id(matchId);
    }




}
