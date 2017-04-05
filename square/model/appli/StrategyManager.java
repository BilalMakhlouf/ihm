package square.model.appli;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import square.model.appli.strategy.Strategies;
import square.util.Contract;
import square.util.Coord;
import square.util.Player;
import square.util.Turn;

class StrategyManager {
    
    // CONSTANTES
    
    /**
     * Nom de la propriété turn (-, Turn, liée).
     * Cette propriété indique qu'un joueur (Turn.player()) vient de jouer en
     *  position Turn.position().
     * Un gestionnaire de stratégies observe cette propriété sur les stratégies
     *  pour la retransmettre à ses propres observateurs.
     */
    public static final String TURN = "turn";

    // ATTRIBUTS
    
    /**
     * Association des stratégies aux joueurs.
     */
    private final Map<Player, IStrategy> strategies;
    
    /**
     * Fait suivre les observations de changement de la pseudo-propriété turn
     *  aux observateurs de ce gestionnaire.
     */
    private final PropertyChangeListener turnObserver;
    
    private final PropertyChangeSupport pcs;

    // CONSTRUCTEURS
    
    StrategyManager() {
        strategies = new EnumMap<Player, IStrategy>(Player.class);
        turnObserver = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                Turn t = (Turn) e.getNewValue();
                updateEnemies(t);
                pcs.firePropertyChange(TURN, null, t);
            }
        };
        pcs = new PropertyChangeSupport(this);
    }

    // COMMANDES

    /**
     * Associe o à ce modèle pour en observer les changements de valeur de la
     *  propriété liée de nom n.
     * @pre
     *     n != null && o != null
     * @post
     *     o est enregistré auprès du modèle pour la propriété n
     */
    public void addPropertyChangeListener(String n, PropertyChangeListener o) {
        Contract.checkCondition(n != null && o != null);
        
        pcs.addPropertyChangeListener(n, o);
    }

    /**
     * Donne la main à la stratégie du joueur p pour qu'il joue un coup.
     * @pre
     *     p != null
     * @post
     *     a fait jouer un tour à la stratégie associée à p
     */
    public void playWith(Player p) {
        Contract.checkCondition(p != null);
        
        strategies.get(p).setMyTurn();
    }

    /**
     * Associe p à une stratégie de nom name.
     * @pre
     *     p != null
     *     name != null
     * @post
     *     p est associé à une stratégie name au sein de ce gestionnaire
     */
    public void setStrategy(String name, Player p, boolean first, int size) {
        Contract.checkCondition(p != null && name != null);
        
        IStrategy s = Strategies.create(name, p, first, size);
        s.addPropertyChangeListener(IStrategy.TURN, turnObserver);
        strategies.put(p, s);
    }
    
    /**
     * Démarre toutes les stratégies.
     */
    public void startStrategies() {
        for (Player p : Player.values()) {
            strategies.get(p).start();
        }
    }
    
    /**
     * Arrête les stratégies en cours d'exécution.
     * Cette méthode attend la terminaison de chaque stratégie arrêtée ce qui
     *  peut être long.
     * Elle ne doit donc pas être exécutée sur EDT.
     */
    public void stopStrategies() {
        Contract.checkCondition(!SwingUtilities.isEventDispatchThread());
        
        for (Player p : Player.values()) {
            strategies.get(p).stop();
        }
    }
    
    // OUTILS
    
    /**
     * Demande à toutes les stratégies qui ne sont pas à l'origine du tour t
     *  de prendre en compte le fait qu'un tour de jeu vient de se passer.
     */
    private void updateEnemies(Turn t) {
        assert t != null && t.player() != null && t.position() != null;
        
        Player crt = t.player();
        Coord k = t.position();
        for (Player p : Player.values()) {
            if (p != crt) {
                strategies.get(p).setEnemyAt(k);
            }
        }
    }
}
