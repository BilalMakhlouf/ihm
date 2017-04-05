package square.util;

public class Turn {
    
    // ATTRIBUTS
    
    private Player player;
    
    private Coord position;
    
    // CONSTRUCTEURS
    
    public Turn(Player p, Coord k) {
        player = p;
        position = k;
    }
    
    // REQUETES
    
    public Player player() {
        return player;
    }
    
    public Coord position() {
        return position;
    }
}
