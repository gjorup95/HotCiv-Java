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
        ArrayList<CityImpl> tempCityValueList = new ArrayList<>(game.getCityMapValues());
        Player cityOwner = tempCityValueList.get(0).getOwner();
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
