package hotciv.standard;

import hotciv.framework.*;

import java.util.ArrayList;

/**
 * @ author Troels Gjørup
 * 14-09-2018
 */
public class WinningConditionBetaCiv implements WinningCondition {
    private final GameImpl game;

    public WinningConditionBetaCiv(GameImpl game) {
        this.game = game;
    }

    @Override
    public Player getWinner() {
        // Creates an ArrayList which contains all the CityImpl from the cityMap in GameImpl.
        ArrayList<CityImpl> tempCityValueList = new ArrayList<>(game.getCityMapValues());
        // Local variable that is inistialized as the first Player who owns a City.
        Player cityOwner = tempCityValueList.get(0).getOwner();
        // Iterates the ArrayList and checks whether the owner of all the cities are identical to the local Player variable.
        for (CityImpl c : tempCityValueList) {
            if (!c.getOwner().equals(cityOwner))
                return null;
        }
        return cityOwner;
    }

    @Override
    public void conquerCity(Position toConquer) {
        if (game.getCityAt(toConquer) != null && game.getCityAt(toConquer).getOwner() != game.getPlayerInTurn()) {
            game.getCityAt(toConquer).setOwner(game.getPlayerInTurn());
        }
    }
}
