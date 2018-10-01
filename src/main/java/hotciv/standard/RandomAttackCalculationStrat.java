package hotciv.standard;
import java.util.Random;

/**
 * @ author Troels Gjørup
 * 01-10-2018
 */
public class RandomAttackCalculationStrat implements AttackCalculationStrat {
    @Override
    public int getCalculationVariable() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
