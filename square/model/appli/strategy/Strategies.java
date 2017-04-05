package square.model.appli.strategy;

import java.lang.reflect.Constructor;

import square.model.appli.IStrategy;
import square.util.Contract;
import square.util.Player;

/**
 * Cette classe est une fabrique de stratégies.
 */
public final class Strategies {
    
    public static final String NAIVE = "NaiveStrategy";
    
    // CONSTRUCTEURS
    
    private Strategies() {
        // rien
    }
    
    /**
     * Créateur de stratégie artificielle de nom n, pour le joueur p qui est le
     *  premier joueur (f == true) ou non (f == false), pour une grille de s
     *  cellules de côté.
     * NB : si la stratégie est humaine, la taille de la grille ne doit pas être
     *  donnée.
     */
    public static IStrategy create(String n, Player p, boolean f, int... s) {
        Contract.checkCondition(p != null);
        
        Class<?>[] types = (s.length == 0)
                ? new Class<?>[] { Player.class, boolean.class }
                : new Class<?>[] { Player.class, boolean.class, int.class };
        Object[] values = (s.length == 0)
                ? new Object[] { p, f }
                : new Object[] { p, f, s[0] };
        return create(n, types, values);
    }
    
    /**
     * Créateur de stratégies pour la classe de nom n dont le constructeur prend
     *  des arguments dont le type et la valeur sont définis par les paramètres
     *  types et vals.
     */
    private static IStrategy create(String n, Class<?>[] types, Object[] vals) {
        String className =
                Strategies.class.getPackage().getName() + "." + n;
        IStrategy s = null;
        try {
            Class<?> c = Class.forName(className);
            Constructor<?> k = c.getDeclaredConstructor(types);
            s = (IStrategy) k.newInstance(vals);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError(e);
        }
        return s;
    }
}
