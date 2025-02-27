package explore.kata.bowling;

interface Try {
    static Try asBonus(Try tryInstance) {
        return new BonusTry(tryInstance);
    }

    default int score(){
        return 0;
    }
    default boolean isStrike(){
        return false;
    }
    default boolean isSpare(){
        return false;
    }
    default boolean isNot(){
        return false;
    }
    default int contribution(){
        return 1;
    }

    static Try of(String character) {
        if (character==null){
            return new NoTry();
        }
        return switch (character) {
            case "X" -> new Strike();
            case "-" -> new Normal(0);
            case "/" -> new Spare();
            case String otherString -> new Normal(Integer.parseInt(otherString));
        };
    }

    record NoTry() implements Try {
        @Override
        public boolean isNot() {
            return true;
        }

        @Override
        public int contribution() {
            return 0;
        }
    }

    record Strike() implements Try {
        @Override
        public int score() {
            return 10;
        }

        @Override
        public boolean isStrike() {
            return true;
        }
    }

    record Normal(int score) implements Try {
    }

    final class Spare implements Try {
        private int score;

        @Override
        public int score(){
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public boolean isSpare() {
            return true;
        }
    }

    record BonusTry(Try tryInstance) implements Try {
        @Override
        public int score() {
            return tryInstance.score();
        }

        @Override
        public boolean isStrike() {
            return tryInstance.isStrike();
        }

        @Override
        public boolean isSpare() {
            return tryInstance.isSpare();
        }

        @Override
        public boolean isNot() {
            return tryInstance.isNot();
        }

        @Override
        public int contribution() {
            return 0;
        }
    }
}
