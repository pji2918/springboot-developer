import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitQuizTest {

    @Test
    void jUnitQuiz1() {
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길금";

        assertThat(name1).isNotNull();
        assertThat(name1).isEqualTo(name2);
        assertThat(name1).isNotEqualTo(name3);
    }

    @Test
    void jUnitQuiz2() {
        int n1 = 15;
        int n2 = 0;
        int n3 = -5;

        // n1 > 0?
        assertThat(n1).isPositive();
        // n3 < 0?
        assertThat(n3).isNegative();
        // n1 > n2?
        assertThat(n1).isGreaterThan(n2);
        // n3 < n2?
        assertThat(n3).isLessThan(n2);
    }
}
