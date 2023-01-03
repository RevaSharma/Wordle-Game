package csci205_hw_wordle;

/**
 * A simple enumerated type that keeps track of the game as it progresses
 */
public enum GameState {

    /**
     * A new game has begun
     */
    NEW_GAME,
    /**
     * The game is currently being played
     */
    GAME_IN_PROGRESS,
    /**
     * Winner has guessed the word correctly in given number of tries
     */
    GAME_WINNER,
    /**
     * Winner was unable to guess the word in the given number of tries
     */
    GAME_LOSER,

}
