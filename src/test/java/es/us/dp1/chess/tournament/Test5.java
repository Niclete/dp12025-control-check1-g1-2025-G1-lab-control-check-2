package es.us.dp1.chess.tournament;

import es.us.dp1.chess.tournament.ai.AIAgentType;
import es.us.dp1.chess.tournament.ai.recommendation.AIAgentRecommendationAlgorithm;
import es.us.dp1.chess.tournament.ai.recommendation.AlmostValidTrainingRecommendationAlgorithm;
import es.us.dp1.chess.tournament.ai.recommendation.CrappyTrainingRecommendationAlgorithm;
import es.us.dp1.chess.tournament.ai.recommendation.DummyAIAgentTypeRecommendationAlgorithm;
import es.us.dp1.chess.tournament.ai.recommendation.StrangeTrainingRecommendationAlgorithm;
import es.us.dp1.chess.tournament.ai.recommendation.ValidAIAgentTypeRecommendationAlgorithm;
import es.us.dp1.chess.tournament.match.ChessMatchRepository;
import es.us.dp1.chess.tournament.recommendation.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;
@DataJpaTest
public class Test5 extends ReflexiveTest {

    @Autowired
    ChessMatchRepository cmr;

    public class WrapperAlgorithm extends AIAgentRecommendationAlgorithm {
        private AIAgentRecommendationAlgorithm algorithm;
        private int numRuns;

        public WrapperAlgorithm(AIAgentRecommendationAlgorithm algorithm) {
            this.algorithm = algorithm;
            this.numRuns = 0;
        }

        public int getNumRuns() {
            return numRuns;
        }

        @Override
        public AIAgentType recommendAIAgentForUser(Integer userId) {
            numRuns++;
            return algorithm.recommendAIAgentForUser(userId);
        }
    }



    @ParameterizedTest
    @MethodSource("provideAlgorithmsAndExpectedResults")
    public void testTrainingRecommendationAlgorithm(AIAgentRecommendationAlgorithm alg, boolean shouldFail){
        // Configure SUT:
        alg.setRepo(cmr);
        AIAgentTypeRecommendationAlgorithmTest cdaTest=new AIAgentTypeRecommendationAlgorithmTest();
        WrapperAlgorithm wrapper = new WrapperAlgorithm(alg);
        cdaTest.setAlgorithm(wrapper);
        int numberOfExecutedTestMethods=0;
        // ExecuteTests
        numberOfExecutedTestMethods=executeTests(cdaTest, shouldFail);
        if(numberOfExecutedTestMethods<1)
            fail("You have not specified any test method!");
        if(wrapper.getNumRuns() < 1)
            fail("The SUT has not been executed in the test!");
    }

    private void executeAfterEach(AIAgentTypeRecommendationAlgorithmTest cdaTest) {
        Method[] methods=cdaTest.getClass().getDeclaredMethods();
        for(Method method:methods){
            if(isMethodAnnotatedWithAfterEach(method)){
                try {
                    method.invoke(cdaTest);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("Error while trying to invoke method:"+method.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private int executeTests(AIAgentTypeRecommendationAlgorithmTest cdaTest, boolean shouldFail) {
        int executed=0;
        Method[] methods=cdaTest.getClass().getDeclaredMethods();
        boolean failDetected=false;
        String message="No test method detected the faulty implementation of the algorithm";
        for(Method method:methods){
            if(isMethodAnnotatedWithTest(method)){
                try {
                    executed++;
                    executeBeforeEach(cdaTest);
                    method.invoke(cdaTest);
                    executeAfterEach(cdaTest);
                }catch(AssertionError assertionError){
                    failDetected=true;
                    message="The test method named "+method.getName()+" failed (and should not)! AsssertionError: "+assertionError.getMessage();
                } catch(InvocationTargetException e){
                    if(e.getTargetException() instanceof org.opentest4j.AssertionFailedError){
                        failDetected=true;
                        message="The test method named "+method.getName()+" failed (and should not)! AsssertionError: "
                                    +((org.opentest4j.AssertionFailedError)e.getTargetException()).getMessage();
                    }else
                        System.out.println("Error while trying to invoke method:"+method.getName());
                }catch (IllegalAccessException | IllegalArgumentException  e) {
                    System.out.println("Error while trying to invoke method:"+method.getName());
                }
            }
        }
        if(failDetected!=shouldFail)
            fail(message);
        return executed;
    }

    private void executeBeforeEach(AIAgentTypeRecommendationAlgorithmTest cdaTest) {
        Method[] methods=cdaTest.getClass().getDeclaredMethods();
        for(Method method:methods){
            if(isMethodAnnotatedWithBeforeEach(method)){
                try {
                    method.invoke(cdaTest);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    System.out.println("Error while trying to invoke method:"+method.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    public static Stream<Arguments> provideAlgorithmsAndExpectedResults(){
        return Stream.of(
            Arguments.of(new ValidAIAgentTypeRecommendationAlgorithm(), false),
            Arguments.of(new DummyAIAgentTypeRecommendationAlgorithm(), true),
            Arguments.of(new CrappyTrainingRecommendationAlgorithm(), true),
            Arguments.of(new StrangeTrainingRecommendationAlgorithm(), true),
            Arguments.of(new AlmostValidTrainingRecommendationAlgorithm(), true)
        );
    }
}
