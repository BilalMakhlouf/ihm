package serie02;

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Map;

/**
 * @inv <pre>
 *     getPodiums() != null
 *     forall r:Rank : getPodiums().get(r) != null
 *     getShotsNb() >= 0
 *     getTimeDelta() >= 0
 *     !isFinished() ==> getTimeDelta() == 0 </pre>
 * @cons <pre>
 *     $ARGS$ Set<E> drawables
 *     $PRE$ (drawables != null && drawables.size() >= 2
 *     $POST$
 *         les modèles des 2+2 podiums sont initialisés aléatoirement
 *         avec les éléments de drawables </pre>
 */
public interface PodiumManager<E extends Drawable> {

    /**
     * Les dispositions des podiums sur la fenêtre :
     * <ul>
     *   <li> WRK_LEFT  : podium gauche de la configuration de départ ;</li>
     *   <li> WRK_RIGHT : podium droit de la configuration de départ ;</li>
     *   <li> OBJ_LEFT  : podium gauche de la configuration objectif ;</li>
     *   <li> OBJ_RIGHT : podium droit de la configuration objectif.</li>
     * </ul>
     */
    enum Rank { WRK_LEFT, WRK_RIGHT, OBJ_LEFT, OBJ_RIGHT }
    
    /**
     * Les ordres passés par le dompteur aux animaux.
     */
    enum Order {
        LO("Lo : G>D"),
        KI("Ki : G<D"),
        MA("Ma :  ^G"),
        NI("Ni :  ^D"),
        SO("So : <->");
        private String label;
        Order(String lbl) { label = lbl; }
        @Override public String toString() { return label; }
    }
    
    // REQUETES
    
    /**
     * Le dernier ordre donné.
     * Vaut null en début de partie.
     */
    Order getLastOrder();

    /**
     * Les quatre podiums gérés par ce gestionnaire.
     */
    Map<Rank, Podium<E>> getPodiums();

    /**
     * Le nombre d'ordres donnés au cours d'une partie.
     */
    int getShotsNb();

    /**
     * L'intervalle de temps entre le début d'une partie et la fin.
     * Vaut 0 tant que la partie n'est pas finie.
     */
    long getTimeDelta();

    /**
     * Indique si une partie en cours est finie.
     */
    boolean isFinished();

    // COMMANDES

    /**
     * @pre <pre>
     *     lst != null </pre>
     * @post <pre>
     *     lst a été ajouté à la liste des écouteurs
     *     de la propriété propName </pre>
     */
    void addPropertyChangeListener(String propName, PropertyChangeListener lst);

    /**
     * @pre <pre>
     *     lst != null </pre>
     * @post <pre>
     *     lst a été ajouté à la liste des écouteurs </pre>
     */
    void addVetoableChangeListener(VetoableChangeListener lst);

    /**
     * Exécute l'ordre o sur ce gestionnaire.
     * @pre <pre>
     *     o != null </pre>
     * @post <pre>
     *     les actions conformes à l'ordre o ont été exécutées sur les podiums
     *       gérés par ce gestionnaire </pre>
     * @throws
     *     PropertyVetoException si l'ordre o a été refusé
     */
    void executeOrder(Order o) throws PropertyVetoException;

    /**
     * Réinitialise ce gestionnaire.
     * @post <pre>
     *     les podiums gérés par ce gestionnaire ont un nouveau modèle
     *       généré aléatoirement
     *     getShotsNb() == 0
     *     getTimeDelta() == 0
     *     getLastOrder() == null </pre>
     */
    void reinit();

    /**
     * @pre <pre>
     *     lst != null </pre>
     * @post <pre>
     *     lst a été retiré de la liste des écouteurs </pre>
     */
    void removePropertyChangeListener(PropertyChangeListener lst);

    /**
     * @pre <pre>
     *     lst != null </pre>
     * @post <pre>
     *     lst a été retiré de la liste des écouteurs </pre>
     */
    void removeVetoableChangeListener(VetoableChangeListener lst);
}
