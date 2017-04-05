package serie06.model;

/**
 * Une boite est une ressource partagée pouvant contenir un entier.
 * Les producteurs mettent des entiers dans une boite, et les consommateurs
 *  les y enlèvent.
 * @inv
 *     isEmpty() <==> getValue() == null
 * @cons
 *     $POST$ isEmpty()
 */
public interface Box {
    
    // REQUETES
    
    /**
     * Le contenu de la boite.
     */
    int getValue();
    
    /**
     * La boite est-elle vide ?
     */
    boolean isEmpty();
    
    // COMMANDES
    
    /**
     * Vide la boite.
     * @post
     *     isEmpty()
     */
    void dump();
    
    /**
     * Remplit la boite avec l'entier v.
     * @pre
     *     isEmpty()
     * @post
     *     getValue() == v
     */
    void fill(int v);
}
