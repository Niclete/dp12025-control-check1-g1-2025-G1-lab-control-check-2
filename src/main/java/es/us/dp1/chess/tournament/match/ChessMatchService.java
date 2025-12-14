package es.us.dp1.chess.tournament.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChessMatchService {

    ChessMatchRepository repo;

    @Autowired
    public ChessMatchService(ChessMatchRepository repo) {
        this.repo = repo;
    }





    @Transactional(readOnly = true)
    public Optional<ChessMatch> getMatchById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public ChessMatch save(ChessMatch m) {
        return repo.save(m);
    }



    @Transactional(readOnly = true)
    public List<ChessMatch> getMatches() {
        return repo.findAll();
    }




}
