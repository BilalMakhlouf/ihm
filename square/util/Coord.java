package square.util;

public class Coord {
    
    // ATTRIBUTS

    private final int row;
    
    private final int column;
    
    // CONSTRUCTEURS
    
    public Coord(int r, int c) {
        row = r;
        column = c;
    }
    
    // REQUETES
    
    public int row() {
        return row;
    }
    
    public int column() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() != obj.getClass()) {
            Coord other = (Coord) obj;
            return column == other.column && row == other.row;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + column;
        result = prime * result + row;
        return result;
    }
}
