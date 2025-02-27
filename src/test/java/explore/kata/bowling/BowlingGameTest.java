package explore.kata.bowling;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameTest {

    @CsvSource({
            "'X X X X X X X X X XXX', 300",
            "'9- 9- 9- 9- 9- 9- 9- 9- 9- 9-', 90",
            "'11 11 11 11 11 11 11 11 11 11', 20",
            "'X 7/ 9- X -8 8/ -6 X X X81', 167",
            "'5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5', 150"
    })
    @ParameterizedTest
    void validLine_shouldReturnScore(String line, int score) {
        assertThat((new BowlingGame(line)).score()).isEqualTo(score);
    }

}
