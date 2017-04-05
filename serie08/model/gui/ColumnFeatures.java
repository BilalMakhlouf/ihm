package serie08.model.gui;

public enum ColumnFeatures {
    
    SUBJECT("Matières", String.class) {
        @Override public String value(String v) { return v; }
        @Override public String defaultValue() { return ""; }
    },
    
    COEF("Coefficients", Double.class) {
        @Override public Double value(String v) {
            return Double.parseDouble(v.trim());
        }
        @Override public Double defaultValue() { return NoteTableModel.ZERO; }
    },
    
    MARK("Notes / 20", Double.class) {
        @Override public Double value(String v) {
            return Double.parseDouble(v.trim());
        }
        @Override public Double defaultValue() { return NoteTableModel.ZERO; }
    },
    
    POINTS("Points", Double.class) {
        @Override public Object value(String v) {
            throw new UnsupportedOperationException();
        }
        @Override public Double defaultValue() {
            throw new UnsupportedOperationException();
        }
    };

    // ATTRIBUTS
    
    private String header;
    private Class<?> cellType;

    // CONSTRUCTEURS
    
    ColumnFeatures(String n, Class<?> c) {
        header = n;
        cellType = c;
    }

    // REQUETES
    
    /**
     * Valeur par défaut pour cette colonne.
     */
    public abstract Object defaultValue();
    
    /**
     * Valeur pour cette colonne.
     */
    public abstract Object value(String v);
    
    /**
     * Entête de cette colonne.
     */
    public String header() {
        return header;
    }
    
    /**
     * Type des cellules constituant cette colonne.
     */
    Class<?> type() {
        return cellType;
    }
}
