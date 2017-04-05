package serie06.model;

import serie06.event.SentenceListener;

/**
 * Un acteur est un objet manipulant une boite.
 * Sa tâche consiste à faire au plus <code>getMaxIterNb()</code> fois
 *  quelquechose sur cette boîte.
 * On démarre l'acteur avec <code>start()</code>, on peut stopper l'acteur
 *  avant qu'il n'ait terminé sa tâche avec
 *  <code>interruptAndWaitForInactivation()</code>.
 * Sinon, l'acteur s'arrête de lui-même quand il a fini sa tâche.
 * Un acteur est un objet qui « fonctionne » tout seul, c'est-à-dire qu'il est
 *  animé par un thread interne, inaccessible de l'extérieur, seulement
 *  pilotable par le biais de certaines méthodes de cette classe.
 * Quand un acteur fait quelquechose avec une boite, il émet des SentenceEvent
 *  qui décrivent ce qu'il fait.
 * @inv
 *     getMaxIterNb() > 0
 *     getBox() != null
 *     getSentenceListeners() != null
 */
public interface Actor {
    
    // REQUETES
    
    /**
     * La boite associée à cet acteur.
     */
    Box getBox();
    
    /**
     * Le nombre maximal de fois que l'acteur peut faire quelque chose avant
     *  de s'arrêter.
     */
    int getMaxIterNb();
    
    /**
     * La séquence des SentenceListeners enregistrés auprès de cet acteur.
     */
    SentenceListener[] getSentenceListeners();
    
    /**
     * Indique si l'acteur est en train de travailler, c'est-à-dire si le thread
     *  qui l'anime a été démarré et n'a pas encore terminé son exécution.
     */
    boolean isActive();
    
    /**
     * Indique si l'acteur est en attente sur la boite au cours de son action.
     */
    boolean isWaitingOnBox();

    // COMMANDES
    
    /**
     * Abonne un SentenceListener auprès de cet acteur.
     */
    void addSentenceListener(SentenceListener listener);
    
    /**
     * Désabonne un SentenceListener de cet acteur.
     */
    void removeSentenceListener(SentenceListener listener);
    
    /**
     * Démarre un acteur, c'est-à-dire crée un nouveau thread interne et lance
     *  son exécution.
     * L'acteur commence à parler.
     * @pre
     *     !isActive()
     * @post
     *     l'action est démarrée
     */
    void start();
    
    /**
     * Interrompt l'acteur puis force le thread courant à attendre la mort du
     *  thread interne de l'acteur avant de continuer.
     * Dès que le thread interne va entrer dans une méthode bloquante, il va
     *  être interrompu et se terminer.
     * En théorie, si le thread interne n'était pas bloqué lors de l'appel de
     *  cette méthode, l'attente pourrait être longue ; en pratique ce ne sera
     *  pas le cas.
     * @pre
     *     isActive()
     * @post
     *     !isActive()
     */
    void interruptAndWaitForInactivation();
}
