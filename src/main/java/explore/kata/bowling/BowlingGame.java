package explore.kata.bowling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherer;

import static java.util.function.Predicate.not;

public record BowlingGame(String line) {

    public int score() {
        return Arrays.stream(line.split(" "))
                .filter(not(String::isBlank))
                .map(Turn::new)
                .gather(Gatherer.<Turn, ScoreCalc, Integer>ofSequential(ScoreCalc::new,
                        (scoreCalc, turn, _) -> scoreCalc.processTurn(turn),
                        (scoreCalc, downstream) -> downstream.push(scoreCalc.total())
                ))
                .mapToInt(Integer::intValue)
                .sum();
    }


    static final class ScoreCalc {

        private int score = 0;
        private final List<Integer> bonusScores = new ArrayList<>(List.of(0, 0));

        public int total() {
            return score;
        }

        public boolean processTurn(Turn turn) {
            this.increaseScoreForTry(turn.firstTry());
            if (turn.isStrike()) {
                this.addBonusScoreForStrike();
            }
            if (turn.isOverAfterFirstTry()) {
                return true;
            }
            increaseScoreForTry(turn.secondTry());
            if (turn.isSpare()) {
                this.addBonusScoreForSpare();
            }
            if (turn.isOverAfterSecondTry()) {
                return true;
            }
            this.increaseScoreForTry( turn.bonusTry());
            return true;
        }

        private void increaseScoreForTry(Try currenttry) {
            score+=(currenttry.contribution() + this.nextBonusScore()) * currenttry.score();
        }

        private int nextBonusScore() {
            bonusScores.addLast(0);
            return bonusScores.removeFirst();
        }

        private void addBonusScoreForStrike() {
            inscreaseBonusByOneAtIndex(0);
            inscreaseBonusByOneAtIndex(1);
        }

        private void inscreaseBonusByOneAtIndex(int index) {
            bonusScores.set(index, bonusScores.get(index) + 1);
        }

        private void addBonusScoreForSpare() {
            inscreaseBonusByOneAtIndex(0);
        }
    }

}
