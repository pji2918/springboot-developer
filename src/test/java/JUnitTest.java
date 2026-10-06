import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ConstantValue")
@DisabledIfEnvironmentVariable(named = "CI", matches = ".*")
public class JUnitTest {
    private static final Logger log = LoggerFactory.getLogger(JUnitTest.class);

    @BeforeAll
    public static void prepareAll() {
        log.info("시작");
    }

    @AfterAll
    public static void cleanUpAll() {
        log.info("끝");
    }

    @BeforeEach
    public void prepare() {
        log.info("준비");
    }

    @AfterEach
    public void cleanUp() {
        log.info("정리");
    }

    @Test
    @DisplayName("1 + 2 == 3")
    public void jUnitTest1() {
        int a = 1;
        int b = 2;
        int sum = 3;

        int result = a + b;

        log.info("sum == result: {}", sum == result);
        Assertions.assertEquals(sum, result);
    }

    @Test
    @DisplayName("1 + 3 == 3")
    public void jUnitTest2() {
        int a = 1;
        int b = 3;

        int result = a + b;

        log.info("result == 3: {}", result == 3);
        Assertions.assertEquals(3, result);
    }
}
