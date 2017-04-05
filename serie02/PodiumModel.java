package serie02;

import javax.swing.event.ChangeListener;

/**
 * Une sorte de pile bornée d'éléments pour laquelle on peut de plus supprimer
 *  l'élément de base tout en haut de la pile, et dont on peut consulter tous
 *  les éléments.
 * @inv <pre>
 *     0 <= capacity()
 *     0 <= size() <= capacity()
 *     top() == elementAt(size() - 1)
 *     bottom() == elementAt(0)
 *     forall i:[0..size()[ : elementAt(i) != null
 *     forall i:[size()..capacity()[ : elementAt(i) == null </pre>
 * @cons <pre>
 * $ARGS$ List<E> init, int capacity
 * $PRE$
 *     init != null
 *     capacity >= init.size()
 *     forall i:[0..init.size()[ : init.get(i) != null
 * $POST$
 *     capacity() == capacity
 *     size() == init.size()
 *     forall i:[0..size()[ : elementAt(i) == init.get(i) </pre>
 */
public interface PodiumModel<E> {
    
    // REQUETES
    
    /**
     * L'élément à la base de ce modèle.
     * @pre <pre>
     *     size() > 0 </pre>
     */
    E bottom();
    
    /**
     * La capacité du modèle, c'est-à-dire le nombre maximal d'éléments
     *  qu'il peut contenir.
     */
    int capacity();
    
    /**
     * Le ième élément du modèle.
     * Les éléments aux positions entre 0 et size()-1 sont tous non null.
     * Il n'y a pas d'élément aux positions suivantes (retourne null).
     * @pre <pre>
     *     0 <= i < capacity() </pre>
     */
    E elementAt(int i);
    
    /**
     * Le nombre d'éléments actuellement dans le modèle.
     */
    int size();
    
    /**
     * L'élément au sommet de ce modèle.
     * @pre <pre>
     *     size() > 0 </pre>
     */
    E top();
    
    // COMMANDES
    
    /**
     * @pre <pre>
     *     cl != null </pre>
     * @post <pre>
     *     cl a été ajouté à la liste des écouteurs </pre>
     */
    void addChangeListener(ChangeListener cl);
    
    /**
     * @pre <pre>
     *     elem != null
     *     size() < capacity() </pre>
     * @post <pre>
     *     size() == old size() + 1
     *     elementAt(size() - 1) == elem </pre>
     */
    void addTop(E elem);
    
    /**
     * @pre <pre>
     *     size() > 0 </pre>
     * @post <pre>
     *     size() == old size() - 1
     *     forall i:[0..size()[
     *         elementAt(i) == old elementAt(i + 1) </pre>
     */
    void removeBottom();
    
    /**
     * @pre <pre>
     *     cl != null </pre>
     * @post <pre>
     *     cl a été retiré de la liste des écouteurs </pre>
     */
    void removeChangeListener(ChangeListener cl);
    
    /**
     * @pre <pre>
     *     size() > 0 </pre>
     * @post <pre>
     *     size() == old size() - 1 </pre>
     */
    void removeTop();
}
