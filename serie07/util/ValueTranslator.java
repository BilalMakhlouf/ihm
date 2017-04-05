package serie07.util;

/**
 * Modélise les traducteurs de texte en valeur, et réciproquement.
 * FiltrablePane utilise un JTextField dans lequel l'utilisateur peut taper du
 *  texte représentant une valeur de type V.
 * FiltrablePane utilise un ValueTranslator pour passer du texte de son
 *  JTextField à la valeur qui lui correspond.
 * Par exemple, pour une Date, le texte pourra être composé sous la forme j/m/a
 *  et un traducteur créera la Date correspondante.
 */
public interface ValueTranslator<V> {
    
    /**
     * Donne la valeur décrite par l'argument text.
     */
    V translateText(String text);
    
    /**
     * Donne la représentation textuelle de value.
     */
    String translateValue(V value);
}
