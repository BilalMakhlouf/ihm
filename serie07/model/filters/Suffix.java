package serie07.model.filters;

import util.Contract;


/**
 * Implantation du filtre (bas� sur des valeurs de type String) :
 *      "getValue() est un suffixe de la valeur filtrable associ�e � e".
 * @inv <pre>
 *     forall e:E :
 *         isValid(e) == e.filtrableValue().endsWith(getValue()) </pre>
 */
public class Suffix<E extends Filterable<String>>
        extends AbstractFilter<E, String> {
    
    // CONSTRUCTEURS
    
    public Suffix() {
        super("");
    }

    // REQUETES
    
    public boolean isValid(E e) {
        Contract.checkCondition(e != null && e.filterableValue() != null);

        return e.filterableValue().endsWith(getValue());
    }
    
    @Override
    public String toString() {
        return "Suffixe";
    }
}
