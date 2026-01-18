package es.us.dp1.chess.tournament.analysis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.us.dp1.chess.tournament.match.ChessMatch;
import es.us.dp1.chess.tournament.match.Federation;
import es.us.dp1.chess.tournament.user.User;

public interface GameAnalysisRepository extends CrudRepository<ChessMatch, Integer> {

    @Query("""
    select u
    from Federation f
    join f.organizedEvents e
    join e.matches m
    join m.winner u
    where f in :federations
      and m.start >= :from
    group by u
    order by count(m) desc, u.id asc
""")
    List<User> findMostWinners(
            @Param("federations") Set<Federation> federations,
            @Param("from") LocalDateTime from
        );
}
