package es.us.dp1.chess.tournament;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.chess.tournament.ai.AIAgent;
import es.us.dp1.chess.tournament.ai.AIAgentEvaluation;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@DataJpaTest()
public class Test1b extends ReflexiveTest{

    @Test
    public void test1bAIAgentMatchAnnotation() {
        checkThatFieldIsAnnotatedWith(AIAgent.class, "matches", OneToMany.class);        
    }

    @Test
    public void test1bAIAgentEvalAidedUserAnnotation() {
        checkThatFieldIsAnnotatedWith(AIAgentEvaluation.class, "aidedUser", ManyToOne.class);
        checkThatFieldIsAnnotatedWith(AIAgentEvaluation.class, "aidedUser", NotNull.class);
    }

    @Test
    public void test2AIAgentEvaluationMatchAnnotation() {
        checkThatFieldIsAnnotatedWith(AIAgentEvaluation.class, "match", ManyToOne.class);
        checkThatFieldIsAnnotatedWith(AIAgentEvaluation.class, "match", NotNull.class);
    }

}
