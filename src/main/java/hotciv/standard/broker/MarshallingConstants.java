package hotciv.standard.broker;

public class MarshallingConstants {

    // Type prefixes
    public static final String GAME_PREFIX = "game";
    public static final String CITY_PREFIX = "city";
    public static final String UNIT_PREFIX = "unit";
    public static final String TILE_PREFIX = "tile";

    // Game methods for marshalling
    public static final String GAME_GET_WINNER = GAME_PREFIX + "_get_winner";
    public static final String GAME_GET_AGE = GAME_PREFIX + "_get_age";
    public static final String GAME_GET_PLAYERINTURN = GAME_PREFIX + "_get_player_in_turn";
    public static final String GAME_END_OF_TURN = GAME_PREFIX + "_end_of_turn";
    public static final String GAME_MOVE_UNIT = GAME_PREFIX + "_move_unit";
    public static final String GAME_CHANGE_PRODUCTION_IN_CITY_AT = GAME_PREFIX + "_change_production_in_city_at";
    public static final String GAME_PERFORM_UNIT_ACTION_AT = GAME_PREFIX + "_perform_unit_action_at";
    public static final String GAME_GET_CITY_AT = GAME_PREFIX + "_get_city_at";
    public static final String GAME_GET_TILE_AT = GAME_PREFIX + "_get_tile_at";
    public static final String GAME_GET_UNIT_AT = GAME_PREFIX + "_get_unit_at";

    // City methods for marshalling
    public static final String CITY_GET_OWNER = CITY_PREFIX + "_get_owner";
    public static final String CITY_GET_SIZE = CITY_PREFIX + "_get_size";
    public static final String CITY_GET_TREASURY = CITY_PREFIX + "_get_treasury";
    public static final String CITY_GET_PRODUCTION = CITY_PREFIX + "_get_production";
    public static final String CITY_GET_WORKFORCE_FOCUS = CITY_PREFIX + "_get_workforce_focus";

    // Unit methods for marshalling
    public static final String UNIT_GET_TYPE_STRING = UNIT_PREFIX + "_get_type_string";
    public static final String UNIT_GET_OWNER = UNIT_PREFIX + "_get_owner";
    public static final String UNIT_GET_MOVE_COUNT = UNIT_PREFIX + "_get_move_count";
    public static final String UNIT_GET_DEFENSIVE_STRENGTH = UNIT_PREFIX + "_get_defensive_strength";
    public static final String UNIT_GET_ATTACK_STRENGTH = UNIT_PREFIX + "_get_attack_strength";

    // Tile methods for marshalling
    public static final String TILE_GET_TYPE_STRING = TILE_PREFIX + "_get_type_string";

}
