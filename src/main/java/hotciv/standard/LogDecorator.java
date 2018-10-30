package hotciv.standard;

import hotciv.framework.*;

/**
 * @ author Troels Gjørup
 * 24-10-2018
 */
public class LogDecorator implements Game {
    private Game game;

    public LogDecorator(Game game){
        this.game = game;

    }
    @Override
    public Tile getTileAt(Position p) {
        return game.getTileAt(p);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return game.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return game.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return game.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return game.getWinner();
    }

    @Override
    public int getAge() {
        return game.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        System.out.println(game.getPlayerInTurn().getColor() + " moves " + getUnitAt(from).getTypeString() + " from " + from + " to " + to + ".");
        return game.moveUnit(from, to);
    }

    @Override
    public void endOfTurn() {
        System.out.println(game.getPlayerInTurn().getColor() + " ends turn.");
        game.endOfTurn();

    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        System.out.println(game.getPlayerInTurn().getColor() + " changed the work focus in city at " + p + " to " + balance + ".");
        game.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        System.out.println(game.getPlayerInTurn().getColor() + " changed production in city at " + p + " to " + unitType + ".");
        game.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        System.out.println(game.getPlayerInTurn().getColor() + " performed unit action at " + p + ".");
        game.performUnitActionAt(p);
    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }
}
