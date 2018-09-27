package hotciv.framework;

/**
 * Represents a player of the game.
 * <p>
 * Responsibilities:
 * 1) To represent a player, specifically his/her color.
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public enum Player {
    // Players are initialized with 0 wins from the start.
    RED(0), BLUE(0), YELLOW(0), GREEN(0);

    // Fields
    private int attackingBattlesWon;

    Player(int attackBattlesWon) {
        this.attackingBattlesWon = attackBattlesWon;
    }

    public int getAttackingBattlesWon() {
        return attackingBattlesWon;
    }

    public void setAttackingBattlesWon(int amount) {
        attackingBattlesWon += amount;
    }
}
