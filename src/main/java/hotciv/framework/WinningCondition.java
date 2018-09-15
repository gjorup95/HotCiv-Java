package hotciv.framework;

/**
 * Represents a winning condition in the game.
 * <p>
 * Responsibilities:
 * 1) Sets the condition for winning a game.
 * <p>
 * Author:
 * Christoffer Ashorn
 * Troels Gjørup
 * Aarhus University
 */

public interface WinningCondition {
    public Player getWinner();
}