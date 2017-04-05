package square.model.appli.strategy;

import square.util.Coord;
import square.util.Player;
import square.util.Contract;

class NaiveStrategy extends AbstractStrategy {
    
    // CONSTANTES
    
    private static final int EMPTY = 1;
    private static final int OCCUPIED = 0;
    private static final boolean DEBUG = false;

    // ATTRIBUTS
    
    private final int size;
    private final int[][] data;
    private int emptyCells;

    // CONSTRUCTEURS
    
    NaiveStrategy(Player p, boolean first, int n) {
        super(p, first);
        size = n;
        data = new int[size][size];
        reinit();
        fillData(EMPTY);
        emptyCells = size * size;
    }
    
    // COMMANDES

    @Override
    public void setEnemyAt(Coord k) {
        Contract.checkCondition(k != null);
        
        data[k.row()][k.column()] = OCCUPIED;
        emptyCells = emptyCells - 1;
    }
    
    // OUTILS

    @Override
    protected Coord computeNextPosition() {
        Contract.checkCondition(emptyCells > 0);
        
        if (DEBUG) debug();
        int r = -1;
        int c = size - 1;
        int n = 0;
        int x = 1 + (int) (Math.random() * emptyCells);
        while (n < x) {
            c = c + 1;
            if (c == size) {
                r = r + 1;
                c = 0;
            }
            if (data[r][c] == EMPTY) {
                n = n + 1;
            }
        }
        data[r][c] = OCCUPIED;
        emptyCells = emptyCells - 1;
        return new Coord(r, c);
    }
    
    @Override
    protected void reinit() {
        fillData(EMPTY);
        emptyCells = size * size;
    }

    private void fillData(int n) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = n;
            }
        }
    }

    private void debug() {
        System.out.println("-----------------");
        System.out.println("tour " + (size * size - emptyCells));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
