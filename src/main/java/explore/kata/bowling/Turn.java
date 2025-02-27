package explore.kata.bowling;

record Turn(Try firstTry, Try secondTry, Try bonusTry) {

    Turn{
        if (secondTry instanceof Try.Spare spare) {
            spare.setScore(10-firstTry.score());
        }
        if (firstTry.isStrike() && !secondTry.isNot()){
            secondTry=Try.asBonus(secondTry);
        }
        bonusTry=Try.asBonus(bonusTry);
    }

    Turn(String frame){
        this(Try.of(charFromFrameAt(frame, 0)), Try.of(charFromFrameAt(frame, 1)), Try.of(charFromFrameAt(frame, 2)));
    }

    private static String charFromFrameAt(String frame, int i) {
        return frame.length() <= i ? null : frame.substring(i,i+1);
    }

    public boolean isStrike() {
        return firstTry.isStrike();
    }

    public boolean isSpare() {
        return secondTry.isSpare();
    }

    public boolean isOverAfterFirstTry() {
        return secondTry.isNot() && bonusTry.isNot();
    }

    public boolean isOverAfterSecondTry() {
        return bonusTry.isNot();
    }
}
