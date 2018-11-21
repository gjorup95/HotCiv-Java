package hotciv.standard.broker;

public class MarshallingConstants {

    // Type prefixes
    public static final String GAME_PREFIX = "game";

    // Game methods for marshalling
    public static final String GAME_GET_WINNER = "game_get_winner";
    public static final String GAME_GET_AGE = "game_get_age";
    public static final String GAME_GET_PLAYERINTURN = "game_get_player_in_turn";
    public static final String GAME_END_OF_TURN = "game_end_of_turn";
    public static final String GAME_MOVE_UNIT = "game_move_unit";
    public static final String GAME_CHANGE_PRODUCTION_IN_CITY_AT = "game_change_production_in_city_at";
    public static final String GAME_PERFORM_UNIT_ACTION_AT = "game_perform_unit_action_at";
    public static final String GAME_GET_CITY_AT = "game_get_city_at";

    // City methods for marshalling
    public static final String CITY_GET_OWNER = "city_get_owner";
    public static final String CITY_GET_SIZE = "city_get_size";
    public static final String CITY_GET_TREASURY = "city_get_treasury";
    public static final String CITY_GET_PRODUCTION = "city_get_production";

    // Unit methods for marshalling
    public static final String UNIT_GET_TYPE_STRING = "unit_get_type_string";
    public static final String UNIT_GET_OWNER = "unit_get_owner";
    public static final String UNIT_GET_MOVE_COUNT = "unit_get_move_count";
    public static final String UNIT_GET_DEFENSIVE_STRENGTH = "unit_get_defensive_strength";
    public static final String UNIT_GET_ATTACK_STRENGTH = "unit_get_attack_strength";

    // Tile methods for marshalling
    public static final String TILE_GET_TYPE_STRING = "tile_get_type_string";
}
