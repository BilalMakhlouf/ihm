package serie06.model;

import serie06.event.SentenceListener;

/**
 * Un acteur est un objet manipulant une boite.
 * Sa t�che consiste � faire au plus <code>getMaxIterNb()</code> fois
 *  quelquechose sur cette bo�te.
 * On d�marre l'acteur avec <code>start()</code>, on peut stopper l'acteur
 *  avant qu'il n'ait termin� sa t�che avec
 *  <code>interruptAndWaitForInactivation()</code>.
 * Sinon, l'acteur s'arr�te de lui-m�me quand il a fini sa t�che.
 * Un acteur est un objet qui ��fonctionne�� tout seul, c'est-�-dire qu'il est
 *  anim� par un thread interne, inaccessible de l'ext�rieur, seulement
 *  pilotable par le biais de certaines m�thodes de cette classe.
 * Quand un acteur fait quelquechose avec une boite, il �met des SentenceEvent
 *  qui d�crivent ce qu'il fait.
 * @inv
 *     getMaxIterNb() > 0
 *     getBox() != null
 *     getSentenceListeners() != null
 */
public interface Actor {
    
    // REQUETES
    
    /**
     * La boite associ�e � cet acteur.
     */
    Box getBox();
    
    /**
     * Le nombre maximal de fois que l'acteur peut faire quelque chose avant
     *  de s'arr�ter.
     */
    int getMaxIterNb();
    
    /**
     * La s�quence des SentenceListeners enregistr�s aupr�s de cet acteur.
     */
    SentenceListener[] getSentenceListeners();
    
    /**
     * Indique si l'acteur est en train de travailler, c'est-�-dire si le thread
     *  qui l'anime a �t� d�marr� et n'a pas encore termin� son ex�cution.
     */
    boolean isActive();
    
    /**
     * Indique si l'acteur est en attente sur la boite au cours de son action.
     */
    boolean isWaitingOnBox();

    // COMMANDES
    
    /**
     * Abonne un SentenceListener aupr�s de cet acteur.
     */
    void addSentenceListener(SentenceListener listener);
    
    /**
     * D�sabonne un SentenceListener de cet acteur.
     */
    void removeSentenceListener(SentenceListener listener);
    
    /**
     * D�marre un acteur, c'est-�-dire cr�e un nouveau thread interne et lance
     *  son ex�cution.
     * L'acteur commence � parler.
     * @pre
     *     !isActive()
     * @post
     *     l'action est d�marr�e
     */
    void start();
    
    /**
     * Interrompt l'acteur puis force le thread courant � attendre la mort du
     *  thread interne de l'acteur avant de continuer.
     * D�s que le thread interne va entrer dans une m�thode bloquante, il va
     *  �tre interrompu et se terminer.
     * En th�orie, si le thread interne n'�tait pas bloqu� lors de l'appel de
     *  cette m�thode, l'attente pourrait �tre longue ; en pratique ce ne sera
     *  pas le cas.
     * @pre
     *     isActive()
     * @post
     *     !isActive()
     */
    void interruptAndWaitForInactivation();
}
