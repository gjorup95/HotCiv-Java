package hotciv.framework;

/**
 * @ author Troels Gjørup
 * 16-09-2018
 */
public interface UnitActions {
    public boolean legalPerformSettlerActionAt(Position performPosition);

    public boolean legalPerformArcherFortifyActionAt(Position performPosition);

}
