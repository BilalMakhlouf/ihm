package square.util;

public class Score {
    
    // ATTRIBUTS
    
    private Player player;
    
    private int score;
    
    // CONSTRUCTEURS
    
    public Score(Player p, int n) {
        player = p;
        score = n;
    }
    
    // REQUETES
    
    public Player player() {
        return player;
    }
    
    public int score() {
        return score;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() != obj.getClass()) {
            Score other = (Score) obj;
            return player == other.player && score == other.score;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        result = prime * result + new Integer(score).hashCode();
        return result;
    }
}
