package serie07.model.filters;

import util.Contract;


/**
 * Implantation du filtre (bas� sur des valeurs de type String) :
 *      "getValue() est un pr�fixe de la valeur filtrable associ�e � e".
 * @inv <pre>
 *     forall e:E :
 *         isValid(e) == e.filtrableValue().startsWith(getValue()) </pre>
 */
public class Prefix<E extends Filterable<String>>
        extends AbstractFilter<E, String> {
    
    // CONSTRUCTEURS
    
    public Prefix() {
        super("");
    }

    // REQUETES
    
    public boolean isValid(E e) {
        Contract.checkCondition(e != null && e.filterableValue() != null);

        return e.filterableValue().startsWith(getValue());
    }
    
    @Override
    public String toString() {
        return "Pr�fixe";
    }
}
