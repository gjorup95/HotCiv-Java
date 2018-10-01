package hotciv.framework;

/**
 * @ author Troels Gjørup
 * 28-09-2018
 */
public class Player {
    private String Color;
    private int attackingBattlesWon;

    public Player(String color) {
        this.Color = color;
        this.attackingBattlesWon = 0;
    }

    public int getAttackingBattlesWon() {
        return attackingBattlesWon;
    }

    public void setAttackingBattlesWon(int attackingBattlesWon) {
        this.attackingBattlesWon = attackingBattlesWon;
    }

    public void resetAttackingBattlesWon() {
        attackingBattlesWon = 0;
    }
}
