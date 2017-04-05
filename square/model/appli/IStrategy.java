package square.model.appli;

import java.beans.PropertyChangeListener;

import square.util.Coord;
import square.util.Player;

/**
 * Une IA doit implanter cette interface pour pouvoir communiquer avec le
 *  modèle de l'application.
 * Une stratégie possède une propriété TURN qui change de valeur chaque fois que
 *  la stratégie joue un tour de jeu.
 * @inv
 *     getPlayer() != null
 */
public interface IStrategy {
    
    // CONSTANTES
    
    /**
     * Nom de la propriété turn (-, Turn, liée).
     * Cette propriété indique que Turn.player() vient de jouer en position
     *  Turn.position().
     */
    String TURN = "turn";
    
    // REQUETES
    
    /**
     * Le joueur associé à cette stratégie.
     */
    Player getPlayer();

    // COMMANDES
    
    /**
     * Associe o à cette stratégie pour en observer les changements de valeur
     *  de la propriété liée de nom n.
     * @pre
     *     n != null && o != null
     * @post
     *     o est enregistré auprès de la stratégie pour la propriété n
     */
    void addPropertyChangeListener(String n, PropertyChangeListener o);
    
    /**
     * Mémorise le fait qu'un ennemi a joué en position k.
     * @pre
     *     k != null
     * @post
     *     Cette stratégie a mémorisé le coup d'un ennemi en position k
     */
    void setEnemyAt(Coord k);
    
    /**
     * Indique à cette stratégie que c'est à son tour de jouer.
     * @post
     *     La stratégie a signalé à son thread interne que c'est à elle de jouer
     */
    void setMyTurn();
    
    /**
     * Démarre cette stratégie qui effectuera autant de tours de jeu que
     *  nécessaire (jusqu'à l'appel de stop()).
     * Un tour de jeu est initié par un appel à setMyTurn() de la part du
     *  gestionnaire de stratégies.
     * À chaque tour de jeu, un calcul est effectué pour déterminer la position
     *  où jouer.
     * Ensuite, la stratégie notifie du coup qu'elle a joué à l'aide d'un Turn
     *  dont le joueur est celui associé à cette stratégie et la position celle
     *  du nouveau coup (un PCE sur la propriété TURN est généré, avec comme
     *  ancienne valeur null et l'instance de Turn décrivant le coup comme
     *  nouvelle valeur).
     * @post
     *     La stratégie vient de démarrer
     *         (son thread interne indique qu'il est RUNNABLE)
     */
    void start();
    
    /**
     * Arrête cette stratégie si ce n'était pas déjà fait.
     * Cette méthode est bloquante jusqu'à la terminaison du thread interne.
     * @post
     *     La stratégie est effectivement arrêtée
     *         (son thread interne indique qu'il est TERMINATED)
     */
    void stop();
}
