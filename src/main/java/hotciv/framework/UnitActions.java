package hotciv.framework;

/**
 * @ author Troels Gjørup
 * 16-09-2018
 */
public interface UnitActions {

    public boolean performSettlerActionAt(Position performPosition);

    public boolean performArcherFortifyActionAt(Position performPosition);

    public void performAction(Position performActionAt);


}
