package es.us.dp1.chess.tournament;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.CrudRepository;

import es.us.dp1.chess.tournament.ai.AIAgent;
import es.us.dp1.chess.tournament.ai.AIAgentEvaluation;
import es.us.dp1.chess.tournament.ai.AIAgentEvaluationRepository;
import es.us.dp1.chess.tournament.ai.AIAgentRepository;
import es.us.dp1.chess.tournament.ai.AIAgentType;
import es.us.dp1.chess.tournament.match.ChessBoard;
import es.us.dp1.chess.tournament.match.ChessMatch;
import es.us.dp1.chess.tournament.user.Authorities;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Enumerated;

@DataJpaTest()
public class Test1a extends ReflexiveTest{

    @Autowired(required = false)
    AIAgentRepository aiAgentRepository;

    @Autowired(required = false)
    AIAgentEvaluationRepository aiAgentEvaluationRepository;

    @Autowired
    EntityManager em;

    @Test
    public void test1aRepositoriesExist(){
        assertNotNull(aiAgentRepository,"The AIAgent repository was not injected into the tests, its autowired value was null");
        assertNotNull(aiAgentEvaluationRepository,"The AIAgentEvaluation repository was not injected into the tests, its autowired value was null");

        assertCrudRepositoryDefinition(AIAgentRepository.class, AIAgent.class);
        assertCrudRepositoryDefinition(AIAgentEvaluationRepository.class, AIAgentEvaluation.class);

        test1aAIAgentRepositoriesContainsMethod();
        test1aAIAgentEvaluationRepositoriesContainsMethod();
    }

    public void test1aAIAgentRepositoriesContainsMethod(){
        if(aiAgentRepository!=null){
            Object v=aiAgentRepository.findById(999);
            assertFalse(null!=v && ((Optional)v).isPresent(), "No result (null) should be returned for an AIAgent that does not exist");
        }else
            fail("The AIAgent repository was not injected into the tests, its autowired value was null");
    }

    public void test1aAIAgentEvaluationRepositoriesContainsMethod(){
        if(aiAgentEvaluationRepository!=null){
            Object v=aiAgentEvaluationRepository.findById(999);
            assertFalse(null!=v && ((Optional)v).isPresent(), "No result (null) should be returned for an AIAgentEvaluation that does not exist");
        }else
            fail("The AIAgentEvaluation repository was not injected into the tests, its autowired value was null");
    }


    @Test
    public void test1CheckAIAgentConstraints() {
         Map<String,List<Object>> invalidValues=Map.of(
                                            "name",     List.of(
                                                    "      ",
                                                    "ab",
                                                    "This agent name is definitely way longer than fifty characters to fail the validation and produce an invalid object state"),
                                            "url", List.of("      ",
                                                    "short",
                                                    "\t\t\t"
                                                    ),
                                            "costPerMatch", List.of(-1.0,-0.01)
                                            );

        AIAgent agent=createValidAIAgent(em);
        em.persist(agent);

        checkThatFieldsAreMandatory(agent, em, "name","url","type","costPerMatch");

        checkThatValuesAreNotValid(agent, invalidValues,em);
    }


    @Test
    public void test1CheckAIAgentEvaluationContraints() {
         Map<String,List<Object>> invalidValues=Map.of(
                                            "opeingAidGrade", List.of(-1,11),
                                            "midGameAidGrade", List.of(-1,15),
                                            "endGameAidGrade", List.of(-5,20)
                                            );

        AIAgentEvaluation evaluation=createValidAIAgentEvaluation(em);
        em.persist(evaluation);

        checkThatFieldsAreMandatory(evaluation, em, "opeingAidGrade","midGameAidGrade","endGameAidGrade");

        checkThatValuesAreNotValid(evaluation, invalidValues,em);
    }

    @Test
    public void test1CheckAnnotations() throws NoSuchFieldException, SecurityException {
        assertTrue(classIsAnnotatedWith(AIAgent.class,Entity.class),"AIAgent must be annotated with @Entity");
        assertTrue(classIsAnnotatedWith(AIAgentEvaluation.class,Entity.class),"AIAgentEvaluation must be annotated with @Entity");
        checkThatFieldIsAnnotatedWith(AIAgent.class,"type",Enumerated.class);
    }

    private void assertCrudRepositoryDefinition(Class<?> repositoryClass, Class<?> entityClass){
        assertTrue(CrudRepository.class.isAssignableFrom(repositoryClass), repositoryClass.getSimpleName()+" should extend CrudRepository");
        ParameterizedType crudType = Arrays.stream(repositoryClass.getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedType)
                .map(type -> (ParameterizedType) type)
                .filter(type -> CrudRepository.class.equals(type.getRawType()))
                .findFirst()
                .orElse(null);
        assertNotNull(crudType, repositoryClass.getSimpleName()+" should directly extend CrudRepository with generic parameters");
        Type[] typeArguments= crudType.getActualTypeArguments();
        assertEquals(entityClass, typeArguments[0], repositoryClass.getSimpleName()+" should use "+entityClass.getSimpleName()+" as entity type");
        assertEquals(Integer.class, typeArguments[1], repositoryClass.getSimpleName()+" should use Integer as id type");
    }

    public static AIAgent createValidAIAgent(EntityManager em){
        ChessMatch m=null;
        if(em!=null){
            m=em.find(ChessMatch.class, 15);
        }else{
            m=createMatch();
        }
        AIAgent agent = new AIAgent();
        agent.setName("ValidAgent");
        agent.setUrl("https://example.ai/agent");
        agent.setType(AIAgentType.EXPERT_SYSTEM);
        agent.setCostPerMatch(10.0);
        agent.setMatches(List.of(m));
        return agent;
    }

    public static AIAgentEvaluation createValidAIAgentEvaluation(EntityManager em){
        ChessMatch m=null;
        if(em!=null){
            m=em.find(ChessMatch.class, 15);
        }else{
            m=createMatch();
        }
        AIAgentEvaluation evaluation = new AIAgentEvaluation();
        evaluation.setOpeingAidGrade(8);
        evaluation.setMidGameAidGrade(7);
        evaluation.setEndGameAidGrade(6);
        evaluation.setMatch(m);
        evaluation.setAidedUser(m.getWhitePlayer());
        return evaluation;
    }



    public static ChessMatch createMatch() {
        ChessMatch match=new ChessMatch();
        match.setName("Test Match");
        match.setStart( LocalDateTime.of(2025, 11, 1, 12, 0));
        match.setFinish( LocalDateTime.of(2025, 11, 1, 13, 0));
        match.setTurnDuration(60L);
        match.setWhitePlayer(createUser("Jose Maestre"));
        match.setBlackPlayer(createUser("Gary Kasparov"));
        match.setWinner(null);
        match.setBoard(new ChessBoard());
        return match;
    }



    public static User createUser(String name){
        Authorities a1=new Authorities();
        a1.setAuthority("PANA");
        User u1=new User();
        setValue(u1,"username",String.class,name);
        setValue(u1, "authority", Authorities.class, a1);
        return u1;
    }

}
