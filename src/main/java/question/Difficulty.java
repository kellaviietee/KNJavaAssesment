package question;

public enum Difficulty {
    EASY(0),
    MEDIUM(1),
    HARD(2);

    private final int difficultyNumber;

    Difficulty(int difficultyNumber) {
        this.difficultyNumber = difficultyNumber;
    }

    public int getDifficultyNumber() {
        return difficultyNumber;
    }
}
