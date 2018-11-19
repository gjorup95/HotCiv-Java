package hotciv.stub;

import hotciv.framework.*;

public class StubTile implements Tile {
  private String type;
  public StubTile(String type) {
    this.type = type;
  }
  public String getTypeString() { return type; }
}
