package square.view;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;

import square.util.Player;

public final class GameColor {
    private static final Color EMPTY = Color.LIGHT_GRAY;
    private static final Map<Player, Color> PLAYERS_TO_COLORS;
    static {
        PLAYERS_TO_COLORS = new EnumMap<Player, Color>(Player.class);
        PLAYERS_TO_COLORS.put(Player.X, Color.YELLOW);
        PLAYERS_TO_COLORS.put(Player.O, Color.CYAN);
    }
    
    private GameColor() {
        // rien
    }
    
    public static Color forPlayer(Player p) {
        if (p == null) {
            return EMPTY;
        } else {
            return PLAYERS_TO_COLORS.get(p);
        }
    }
}
