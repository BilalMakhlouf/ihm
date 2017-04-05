package serie07.model.filters;

import util.Contract;


/**
 * Implantation du filtre (basé sur des valeurs de type String) :
 *      "getValue() est un suffixe de la valeur filtrable associée à e".
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
