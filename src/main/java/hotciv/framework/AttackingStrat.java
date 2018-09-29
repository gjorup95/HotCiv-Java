package hotciv.framework;

/**
 * @ author Troels Gjørup
 * 28-09-2018
 */
public interface AttackingStrat {

public int calculateAttackerStr (Position from);

public int calculateDefensiveStr(Position to);

public int attackResult(Position from, Position to);

public boolean attack (Position from, Position to);
}
