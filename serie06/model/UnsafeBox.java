package serie06.model;

import util.Contract;

/**
 * Implantation non « thread safe » des boîtes.
 */
public class UnsafeBox implements Box {
    
    // ATTRIBUTS
    
    private Integer value;

    // CONSTRUCTEURS
    
    public UnsafeBox() {
        value = null;
    }

    // REQUETES
    
    public int getValue() {
        return value;
    }
    
    public boolean isEmpty() {
        return value == null;
    }
    
    // COMMANDES
    
    public void dump() {
        value = null;
    }
    
    public void fill(int v) {
        Contract.checkCondition(isEmpty());
        
        value = v;
    }
}
