package square.model.appli;

import java.beans.PropertyChangeListener;

import square.util.Coord;
import square.util.Player;

/**
 * @inv
 *     getFirstPlayer() != null
 *     getPlayer() != null
 *     getState() != null
 *     isEmpty() <==> forall k : getPlayerAt(k) == null
 *     isFull() <==> forall k : getPlayerAt(k) != null
 *     isValidIndex(n) <==> 0 <= n < size()
 *     isValidPosition(k) <==> isValidIndex(k.row()) && isValidIndex(k.column())
 *     size() >= MIN_SIZE
 *     forall p : getScoreOf(p) >= 0
 * @cons
 *     $ARGS$ int s, Player first
 *     $PRE$  s >= MIN_SIZE && first != null
 *     $POST$
 *         getFirstPlayer() == first
 *         getPlayer() == first
 *         isEmpty()
 *         size() == s
 *         forall p : getScoreOf(p) == 0
 */
public interface ISquareGameModel {
    
    // CONSTANTES
    
    /**
     * Nom de la propriété empty (R, boolean, liée).
     */
    String EMPTY = "empty";
    
    /**
     * Nom de la propriété full (R, boolean, liée).
     */
    String FULL = "full";
    
    /**
     * Nom de la propriété scoreOf indexée par Player (R, int, liée).
     */
    String SCORE_OF = "scoreOf";
    
    /**
     * Nom de la propriété player (RW, Player, liée).
     */
    String PLAYER = "player";
    
    /**
     * Nom de la propriété turn (-, Turn, liée).
     * Cette propriété indique qu'un joueur (Turn.player()) vient de jouer en
     *  position Turn.position().
     * Un modèle observe cette propriété sur son gestionnaire de stratégies
     *  pour la retransmettre à ses propres observateurs.
     */
    String TURN = "turn";
    
    /**
     * Nom de la propriété state (R, State, liée).
     */
    String STATE = "state";
    
    /**
     * Taille minimale du modèle.
     */
    int MIN_SIZE = 3;
    
    /**
     * Type indiquant l'état du modèle lorsqu'il stoppe ou démarre les parties.
     * STOPPING : une partie est en cours d'arrêt
     * STOPPED : toute partie est arrêtée
     * STARTED : une partie est démarrée
     */
    enum State { STOPPING, STOPPED, STARTED }
    
    // REQUETES
    
    /**
     * Le joueur qui commence les parties.
     */
    Player getFirstPlayer();
    
    /**
     * Le joueur dont c'est le tour.
     */
    Player getPlayer();
    
    /**
     * Le joueur en position k ou null s'il n'y en a pas.
     * @pre
     *     k != null
     */
    Player getPlayerAt(Coord k);
    
    /**
     * Le nombre de carrés obtenus par le joueur p.
     * @pre
     *     p != null
     */
    int getScoreOf(Player p);
    
    /**
     * L'état du modèle relativement au démarrage ou à l'arrêt des stratégies.
     */
    State getState();
    
    /**
     * Indique si ce modèle est vide.
     */
    boolean isEmpty();
    
    /**
     * Indique si ce modèle est plein.
     */
    boolean isFull();
    
    /**
     * Indique si n est un index de l igne ou de colonne valide pour ce modèle.
     */
    boolean isValidIndex(int n);
    
    /**
     * Indique si k est une position valide pour ce modèle.
     * @pre
     *     k != null
     */
    boolean isValidPosition(Coord k);
    
    /**
     * Le nombre de lignes et de colonnes du modèle.
     */
    int size();
    
    // COMMANDES
    
    /**
     * Associe o à ce modèle pour en observer les changements de valeur de la
     *  propriété liée de nom n.
     * @pre
     *     n != null && o != null
     * @post
     *     o est enregistré auprès du modèle pour la propriété n
     */
    void addPropertyChangeListener(String n, PropertyChangeListener o);
    
    /**
     * Associe à p la stratégie s.
     * @pre
     *     p != null
     *     strategyName != null
     *     isEmpty()
     * @post
     *     p est doté de la stratégie strategyName
     */
    void plugStrategy(String strategyName, Player p);
    
    /**
     * Fixe le joueur qui commence.
     * @pre
     *     p != null
     *     isEmpty()
     * @post
     *     getFirstPlayer() == p
     */
    void setFirstPlayer(Player p);
    
    /**
     * Fait passer le modèle au joueur suivant.
     * @post
     *     getFirstPlayer() == old getFirstPlayer()
     *     getPlayer() == old getPlayer().getNext()
     *     forall c : getPlayerAt(c) == old getPlayerAt(c)
     *     size() == old size()
     *     getScoreOf(old getPlayer()) == old getScoreOf(getPlayer())
     */
    void setPlayer();
    
    /**
     * Fait jouer le joueur courant en position k.
     * @pre
     *     k != null && isValidPosition(k)
     *     getPlayerAt(k) == null
     * @post
     *     getFirstPlayer() == old getFirstPlayer()
     *     getPlayer() == old getPlayer().getNext()
     *     getPlayerAt(k) == old getPlayer()
     *     forall c != k : getPlayerAt(c) == old getPlayerAt(c)
     *     size() == old size()
     *     getScoreOf(old getPlayer()) a augmenté du nombre de carrés
     *         créés par ce coup
     */
    void setPlayerAt(Coord k);
    
    /**
     * Démarre une nouvelle partie.
     * Nettoie le modèle et remet les scores à 0.
     * Si une partie est en cours, l'arrête avant d'en démarrer une nouvelle.
     * @post
     *     isEmpty()
     *     size() == old size()
     *     getFirstPlayer() == old getFirstPlayer()
     *     getPlayer() == getFirstPlayer()
     *     forall p : getScoreOf(p) == 0
     *     lance une nouvelle partie...
     */
    void startPlaying();
    
    /**
     * Arrête une partie.
     * @post
     *     arrête la partie en cours
     */
    void stopPlaying();
}
