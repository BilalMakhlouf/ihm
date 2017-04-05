package square.util;

/**
 * Quand on cherche si un coup en position k ferme un carré, il faut tester le
 *  contenu de la grille tout autour de la position k.
 * Cette classe permet de tester les quatre zones carrées autour de k :
 * +-+-+   k en 1 -> zone TL (Top Left)
 * |1|2|   k en 2 -> zone TR (Top Right)
 * |3|4|   k en 3 -> zone BL (Bottom Left)
 * +-+-+   k en 4 -> zone BR (Bottom Right)
 * 
 */
public enum Zone {
    TL(new int[][] {{0, 1}, {1, 0}, {1, 1}}),
    TR(new int[][] {{0, -1}, {1, -1}, {1, 0}}),
    BL(new int[][] {{-1, 0}, {-1, 1}, {0, 1}}),
    BR(new int[][] {{-1, -1}, {-1, 0}, {0, -1}});
    
    /**
     * Déplacements à tester autour de k.
     */
    private final int[][] offsets;
    
    Zone(int[][] t) {
        offsets = t;
    }
    
    public int[][] offsets() {
        return offsets;
    }
}
